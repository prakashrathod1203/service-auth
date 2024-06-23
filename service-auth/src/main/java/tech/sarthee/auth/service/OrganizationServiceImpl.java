package tech.sarthee.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.sarthee.auth.library.exception.ResourceAlreadyExistsException;
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.request.OrganizationRequest;
import tech.sarthee.auth.library.model.dto.response.OrganizationResponse;
import tech.sarthee.auth.library.util.CommonUtils;
import tech.sarthee.auth.model.entity.OrganizationEntity;
import tech.sarthee.auth.repository.OrganizationRepository;
import tech.sarthee.auth.util.Translator;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    public OrganizationResponse createOrganization(OrganizationRequest request) throws ResourceAlreadyExistsException {
        log.debug("Creating organization with request: {}", request);

        var optionalOrganizationEntity = organizationRepository.findById(request.getOrganizationId());
        if(optionalOrganizationEntity.isPresent()) {
            log.error("Organization with id {} already exists", request.getOrganizationId());
            throw new ResourceAlreadyExistsException(Translator.toLocale("operation.exists"));
        }

        var organizationEntity = CommonUtils.objectToPojoConverter(request, OrganizationEntity.class);
        organizationEntity = organizationRepository.save(Objects.requireNonNull(organizationEntity));
        log.debug("Organization created successfully with id: {}", organizationEntity.getOrganizationId());
        return CommonUtils.objectToPojoConverter(request, OrganizationResponse.class);
    }

    @Override
    public OrganizationResponse updateOrganization(String id, OrganizationRequest request)  throws ResourceNotFoundException  {
        log.debug("Updating organization with id: {} and request: {}", id, request);
        var dbOrganizationEntity = organizationRepository.findById(id.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound")));
        var organizationEntity = CommonUtils.objectToPojoConverter(request, OrganizationEntity.class);
        Objects.requireNonNull(organizationEntity).setOrganizationId(dbOrganizationEntity.getOrganizationId());
        organizationEntity = organizationRepository.save(organizationEntity);
        log.debug("Organization updated successfully with id: {}", organizationEntity.getOrganizationId());
        return CommonUtils.objectToPojoConverter(organizationEntity, OrganizationResponse.class);
    }

    @Override
    public void deleteOrganization(String id) throws ResourceNotFoundException {
        log.debug("Deleting organization with id: {}", id);
        var organizationEntity = organizationRepository.findById(id.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound")));
        organizationRepository.delete(organizationEntity);
        log.debug("Organization deleted successfully with id: {}", id);
    }

    @Override
    public OrganizationResponse fetchOrganizationById(String id) throws ResourceNotFoundException {
        log.debug("Fetching organization with id: {}", id);
        var organizationEntity = organizationRepository.findById(id.toUpperCase())
                .orElseThrow(() -> new ResourceNotFoundException(Translator.toLocale("error.resourceNotFound")));
        log.debug("Organization fetched successfully with id: {}", id);
        return CommonUtils.objectToPojoConverter(organizationEntity, OrganizationResponse.class);
    }

    @Override
    public List<OrganizationResponse> fetchAllOrganizations() {
        log.debug("Received request to get all organizations");
        var organizations = organizationRepository.findAll();
        log.debug("Organizations fetched successfully");
        return CommonUtils.objectToPojoConverter(organizations, OrganizationResponse.class);
    }
}
