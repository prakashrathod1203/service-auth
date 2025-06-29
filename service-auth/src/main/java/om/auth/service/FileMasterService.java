package om.auth.service;

import org.springframework.web.multipart.MultipartFile;
import om.auth.library.enums.EntityNameEnum;
import om.auth.library.exception.ResourceNotFoundException;
import om.auth.library.model.dto.response.FileMasterResponse;
import om.auth.model.entity.FileMasterEntity;

import java.io.IOException;
import java.util.List;

public interface FileMasterService {

    FileMasterResponse deleteById(Long id) throws ResourceNotFoundException;

    FileMasterResponse findById(Long id) throws ResourceNotFoundException;

    FileMasterEntity findFileMasterById(Long id) throws ResourceNotFoundException;

    List<FileMasterResponse> findByEntityIdAndEntityName(String id, EntityNameEnum entityName)
            throws ResourceNotFoundException;

    List<FileMasterResponse> findByEntityIdAndEntityNames(String id,
            List<EntityNameEnum> entityNameEnums) throws ResourceNotFoundException;

    List<FileMasterResponse> findByEntityId(String id) throws ResourceNotFoundException;

    FileMasterResponse upload(MultipartFile multipartFile, String fileName, String fileType,
            String entityId, EntityNameEnum entityName) throws IOException;

    void saveAll(List<FileMasterEntity> list);
}
