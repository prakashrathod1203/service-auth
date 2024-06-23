package tech.sarthee.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.sarthee.auth.constant.SwaggerConstants;
import tech.sarthee.auth.library.constant.ResourceEndpoint;
import tech.sarthee.auth.library.enums.EntityNameEnum;
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.response.FileMasterResponse;
import tech.sarthee.auth.library.model.dto.response.RestApiResponse;
import tech.sarthee.auth.model.entity.FileMasterEntity;
import tech.sarthee.auth.service.FileMasterService;
import tech.sarthee.auth.util.Translator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@Validated
@RequestMapping(ResourceEndpoint.FILE)
@RequiredArgsConstructor
@Slf4j
public class FileMasterController {

    private final FileMasterService fileMasterService;

    /**
     * Handles file upload requests.
     * This method uploads a file to the server.
     */
    @Operation(
            summary = SwaggerConstants.UPLOAD_FILE_SUMMARY,
            description = SwaggerConstants.UPLOAD_FILE_DESCRIPTION,
            tags = {SwaggerConstants.FILE_MANAGER_TAG}
    )
    @PostMapping(
            value = ResourceEndpoint.FILE_UPLOAD,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestApiResponse> upload(
            @RequestPart("file") MultipartFile multipartFile,
            @RequestParam("fileName") String fileName,
            @RequestParam("fileType") String fileType,
            @RequestParam @Parameter(schema = @Schema(allowableValues = {"QR_CODE", "BULK_QR_CODE", "USER_PROFILE"})) EntityNameEnum entityName,
            @RequestParam("entityId") String entityId
    ) throws IOException {
        log.info("Received file upload request with fileName: {}, fileType: {}, entityName: {}, entityId: {}", fileName, fileType, entityName, entityId);

        FileMasterResponse fileMasterResponse = fileMasterService.upload(multipartFile, fileName, fileType, entityId, entityName);
        log.info("File uploaded successfully: {}", fileMasterResponse);
        RestApiResponse response = new RestApiResponse(Translator.toLocale("file.upload.success"), fileMasterResponse, true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Handles file download requests.
     * This method download a file from the server.
     */
    @Operation(
            summary = SwaggerConstants.DOWNLOAD_FILE_BY_ID_SUMMARY,
            description = SwaggerConstants.DOWNLOAD_FILE_BY_ID_DESCRIPTION,
            tags = {SwaggerConstants.GENERATE_QR_TAG, SwaggerConstants.FILE_MANAGER_TAG}
    )
    @GetMapping(
            value = ResourceEndpoint.FILE_DOWNLOAD,
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<?> download(
            @PathVariable(value = "id", required = true) Long id
    ) throws ResourceNotFoundException, MalformedURLException {
        log.info("Received request to download file with id: {}", id);
        FileMasterEntity fileMaster = fileMasterService.findFileMasterById(id);
        Path dirPath = Paths.get(fileMaster.getFileStorePath());
        log.debug("Resource created with URI: {}", dirPath.toUri());
        Resource resource = new UrlResource(dirPath.toUri());
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
        log.debug("Header value set to: {}", headerValue);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

    /**
     * Fetches a list of files associated with a given entity.
     * This method retrieves a list of files by their entity ID and entity name.
     */
    @Operation(
            summary = SwaggerConstants.FETCH_FILE_LIST_BY_ENTITY_SUMMARY,
            description = SwaggerConstants.FETCH_FILE_LIST_BY_ENTITY_DESCRIPTION,
            tags = {SwaggerConstants.FILE_MANAGER_TAG}
    )
    @GetMapping(
            consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    public ResponseEntity<RestApiResponse> findByEntityId(
            @RequestParam(value = "entityId", required = true) String entityId,
            @RequestParam(value = "entityName", required = true) EntityNameEnum entityNameEnum
    ) throws ResourceNotFoundException {
        log.info("Received request to find files with entityId: {} and entityName: {}", entityId, entityNameEnum);
        List<FileMasterResponse> fileMasterResponses = fileMasterService.findByEntityIdAndEntityName(entityId, entityNameEnum);
        RestApiResponse response = new RestApiResponse(Translator.toLocale("operation.getList"), fileMasterResponses, true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Deletes a file by its ID.
     * This method deletes a file identified by its ID.
     */
    @Operation(
            summary = SwaggerConstants.DELETE_FILE_BY_ID_SUMMARY,
            description = SwaggerConstants.DELETE_FILE_BY_ID_DESCRIPTION,
            tags = {SwaggerConstants.FILE_MANAGER_TAG}
    )
    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestApiResponse> delete(
            @PathVariable("id") @Min(1) Long id
    ) throws ResourceNotFoundException {
        log.info("Received request to delete file with id: {}", id);
        FileMasterResponse fileMasterResponse = fileMasterService.deleteById(id);
        log.info("File deleted successfully: {}", fileMasterResponse);
        RestApiResponse response = new RestApiResponse(Translator.toLocale("operation.delete"), fileMasterResponse, true);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
