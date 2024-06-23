package tech.sarthee.auth.service;

import tech.sarthee.auth.library.exception.ResourceAlreadyExistsException;
import tech.sarthee.auth.library.exception.ResourceNotFoundException;
import tech.sarthee.auth.library.model.dto.request.OrganizationRequest;
import tech.sarthee.auth.library.model.dto.response.OrganizationResponse;

import java.util.List;

public interface OrganizationService {
    OrganizationResponse createOrganization(OrganizationRequest request) throws ResourceAlreadyExistsException;
    OrganizationResponse updateOrganization(String id, OrganizationRequest request) throws ResourceNotFoundException;
    void deleteOrganization(String id) throws ResourceNotFoundException;
    OrganizationResponse fetchOrganizationById(String id) throws ResourceNotFoundException;
    List<OrganizationResponse> fetchAllOrganizations();
}