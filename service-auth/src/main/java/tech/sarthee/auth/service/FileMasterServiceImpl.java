package tech.sarthee.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.sarthee.auth.library.constant.ResourceEndpoint;
import tech.sarthee.auth.library.enums.EntityNameEnum;
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.response.FileMasterResponse;
import tech.sarthee.auth.repository.FileMasterRepository;
import tech.sarthee.auth.model.entity.FileMasterEntity;
import tech.sarthee.auth.library.util.CommonUtils;
import tech.sarthee.auth.util.Translator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@RequiredArgsConstructor
public class FileMasterServiceImpl implements FileMasterService {

    private final FileMasterRepository fileMasterRepository;

    @Value("${file.storage.path}")
    private String storagePath;

    @Override
    public FileMasterResponse deleteById(Long id) throws ResourceNotFoundException {
        log.debug("Attempting to delete file with id: {}", id);

        FileMasterEntity fileMaster = fileMasterRepository.findById(id).orElseThrow(() -> {
            log.error("File with id {} not found", id);
            return new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound"));
        });

        log.debug("File with id {} found: {}", id, fileMaster);

        fileMasterRepository.delete(fileMaster);
        log.debug("File with id {} deleted", id);

        FileMasterResponse fileMasterResponse = CommonUtils.objectToPojoConverter(fileMaster, FileMasterResponse.class);
        log.debug("Converted file entity to response: {}", fileMasterResponse);

        return fileMasterResponse;
    }

    @Override
    public FileMasterResponse findById(Long id) throws ResourceNotFoundException {
        log.debug("Finding FileMasterEntity by id: {}", id);
        FileMasterEntity fileMaster = fileMasterRepository.findById(id).orElseThrow(() -> {
            log.warn("FileMasterEntity not found for id in findById method: {}", id);
            return new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound"));
        });
        FileMasterResponse fileMasterResponse = CommonUtils.objectToPojoConverter(fileMaster, FileMasterResponse.class);
        log.debug("FileMasterEntity found in findById method: {}", fileMaster);
        return fileMasterResponse;
    }

    @Override
    public FileMasterEntity findFileMasterById(Long id) throws ResourceNotFoundException {
        log.debug("Attempting to find FileMasterEntity with id: {}", id);
        FileMasterEntity fileMaster = fileMasterRepository.findById(id).orElseThrow(() -> {
            log.warn("FileMasterEntity not found for id in findFileMasterById method: {}", id);
            return new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound"));
        });
        log.debug("FileMasterEntity found in findFileMasterById method: {}", fileMaster);
        return fileMaster;
    }

    @Override
    public List<FileMasterResponse> findByEntityIdAndEntityName(String id, EntityNameEnum entityName) throws ResourceNotFoundException {
        log.debug("No FileMasterEntity found for entityId: {} and entityName: {}", id, entityName);
        List<FileMasterEntity> fileMasters = fileMasterRepository.findByEntityIdAndEntityName(id, entityName);
        if (fileMasters.isEmpty()) {
            log.warn("Finding FileMasterEntity objects by entityId: {} and entityName: {}", id, entityName);
            throw new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound"));
        }
        List<FileMasterResponse> fileMasterResponses = new ArrayList<>(fileMasters.size());
        fileMasters.forEach(fileMaster -> {
            FileMasterResponse fileMasterResponse = CommonUtils.objectToPojoConverter(fileMaster, FileMasterResponse.class);
            String downloadUrl = String.format("%s%s", ResourceEndpoint.FILE, ResourceEndpoint.FILE_DOWNLOAD.replace("{id}", String.valueOf(fileMaster.getId())));
            Objects.requireNonNull(fileMasterResponse).setDownloadUrl(downloadUrl);
            fileMasterResponses.add(fileMasterResponse);
        });
        log.debug("Found {} FileMasterEntity objects for entityId: {} and entityName: {}", fileMasters.size(), id, entityName);
        return fileMasterResponses;
    }

    @Override
    public List<FileMasterResponse> findByEntityIdAndEntityNames(String id, List<EntityNameEnum> entityNames) throws ResourceNotFoundException {
        log.debug("Entering findByEntityIdAndEntityNames with id: {} and entityNames: {}", id, entityNames);
        List<FileMasterEntity> fileMasters = fileMasterRepository.findByEntityIdAndEntityNames(id, entityNames);
        if (fileMasters.isEmpty()) {
            log.warn("No file masters found for id: {} and entityNames: {}", id, entityNames);
            throw new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound"));
        }
        List<FileMasterResponse> fileMasterResponses = new ArrayList<>(fileMasters.size());
        fileMasters.forEach(fileMaster -> {
            FileMasterResponse fileMasterResponse = CommonUtils.objectToPojoConverter(fileMaster, FileMasterResponse.class);
            Objects.requireNonNull(fileMasterResponse).setDownloadUrl(String.format("%s%s", ResourceEndpoint.FILE, ResourceEndpoint.FILE_DOWNLOAD.replace("{id}", String.valueOf(fileMaster.getId()))));
            fileMasterResponses.add(fileMasterResponse);
        });
        log.debug("Exiting findByEntityIdAndEntityNames with responses: {}", fileMasterResponses);
        return fileMasterResponses;
    }

    @Override
    public List<FileMasterResponse> findByEntityId(String id) throws ResourceNotFoundException {
        List<FileMasterEntity> fileMasters = fileMasterRepository.findByEntityId(id);
        if (fileMasters.isEmpty()) {
            log.debug("No FileMasterEntity found for entityId: {}", id);
            throw new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound"));
        }
        List<FileMasterResponse> fileMasterResponses = new ArrayList<>(fileMasters.size());
        fileMasters.forEach(fileMaster -> {
            FileMasterResponse fileMasterResponse = CommonUtils.objectToPojoConverter(fileMaster, FileMasterResponse.class);
            String downloadUrl = String.format("%s%s", ResourceEndpoint.FILE, ResourceEndpoint.FILE_DOWNLOAD.replace("{id}", String.valueOf(fileMaster.getId())));
            Objects.requireNonNull(fileMasterResponse).setDownloadUrl(downloadUrl);
            fileMasterResponses.add(fileMasterResponse);
        });
        log.debug("Found {} FileMasterEntity objects for entityId: {}", fileMasters.size(), id);
        return fileMasterResponses;
    }

    @Override
    public FileMasterResponse upload(MultipartFile multipartFile, String fileName, String fileType, String entityId, EntityNameEnum entityName) throws IOException {
        // Build file path
        String filePathBuilder = storagePath + File.separator + entityId + File.separator + entityName;

        // Create upload directory if it doesn't exist
        Path uploadPath = Paths.get(filePathBuilder);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
                log.debug("Created upload directory: {}", uploadPath);
            } catch (IOException e) {
                log.error("Failed to create upload directory: {}", uploadPath, e);
                throw e;
            }
        }

        // Append timestamp and original filename to upload path
        String timestamp = String.valueOf(new java.sql.Timestamp(System.currentTimeMillis()).getTime());
        uploadPath = uploadPath.resolve(timestamp + "-" + multipartFile.getOriginalFilename());

        // Perform file upload
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, uploadPath, StandardCopyOption.REPLACE_EXISTING);
            log.debug("File uploaded successfully to: {}", uploadPath);

            // Save file metadata to database
            FileMasterEntity fileMaster = new FileMasterEntity();
            fileMaster.setFileName(fileName);
            fileMaster.setFileType(fileType);
            fileMaster.setEntityId(entityId);
            fileMaster.setEntityName(entityName);
            fileMaster.setFileStorePath(uploadPath.toString());
            fileMaster = fileMasterRepository.save(fileMaster);
            log.debug("File metadata saved to database. File ID: {}", fileMaster.getId());

            // Create response object with download URL
            FileMasterResponse fileMasterResponse = CommonUtils.objectToPojoConverter(fileMaster, FileMasterResponse.class);
            String downloadUrl = String.format("%s%s", ResourceEndpoint.FILE, ResourceEndpoint.FILE_DOWNLOAD.replace("{id}", String.valueOf(fileMaster.getId())));
            Objects.requireNonNull(fileMasterResponse).setDownloadUrl(downloadUrl);
            log.debug("Generated download URL: {}", downloadUrl);

            return fileMasterResponse;
        }
    }

    @Override
    public void saveAll(List<FileMasterEntity> list) {
        log.debug("Saving a list of FileMasterEntity");
        fileMasterRepository.saveAll(list);
        log.debug("Saved {} FileMasterEntity objects successfully.", list.size());
    }
}
