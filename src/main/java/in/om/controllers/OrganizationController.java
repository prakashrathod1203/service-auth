package in.om.controllers;

import in.om.component.ApiResponseDoc;
import in.om.component.Translator;
import in.om.constants.CentralAuthResourceEndpoint;
import in.om.dtos.OrganizationDTO;
import in.om.response.ResponseBody;
import in.om.services.OrganizationService;
import in.om.vos.OrganizationVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Prakash Rathod
 */
@Api(tags = "Organization", value = "Organization Controller")
@RestController
@RequestMapping(CentralAuthResourceEndpoint.ORG)
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @ApiResponseDoc
    @ApiOperation(value = "Fetch Organization", response = ResponseBody.class)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> fetchOrganization(@PathVariable("id")String id) {
        OrganizationVO organizationVO = organizationService.fetchOrganization(id);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("role.added.success"), organizationVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Fetch Organizations", response = ResponseBody.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> fetchOrganizations() {
        List<OrganizationVO> organizationVOList = organizationService.fetchOrganizations();
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("role.added.success"), organizationVOList,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Create Organizations", response = ResponseBody.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> create(@RequestBody OrganizationDTO organizationDTO){
        OrganizationVO organizationVO = organizationService.create(organizationDTO);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("role.added.success"), organizationVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Update Organizations", response = ResponseBody.class)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> update(@PathVariable("id") String id, @RequestBody OrganizationDTO organizationDTO) {
        OrganizationVO organizationVO = organizationService.update(id, organizationDTO);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("role.added.success"), organizationVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
