package in.om.controllers;

import in.om.component.ApiResponseDoc;
import in.om.component.Translator;
import in.om.constants.CentralAuthResourceEndpoint;
import in.om.dtos.RoleDTO;
import in.om.response.ResponseBody;
import in.om.services.RoleService;
import in.om.vos.RoleVO;
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
@Api(tags = "Role", value = "Role Controller")
@RestController
@RequestMapping(CentralAuthResourceEndpoint.ROLE)
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @ApiResponseDoc
    @ApiOperation(value = "Fetch Role", response = ResponseBody.class)
    @GetMapping(path = CentralAuthResourceEndpoint.ROLE_V1_FETCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> fetchRole(@PathVariable("groupId") String groupId,
                                                   @PathVariable("id") String id) {
        RoleVO roleVO = roleService.fetchRole(groupId, id);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.fetch.successfully"), roleVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Fetch Roles", response = ResponseBody.class)
    @GetMapping(path = CentralAuthResourceEndpoint.ROLE_V1_FETCH_ALL, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> fetchGroups(@PathVariable("groupId") String groupId) {
        List<RoleVO> roleVOList = roleService.fetchRoles(groupId);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.fetch.successfully"), roleVOList,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Create Role", response = ResponseBody.class)
    @PostMapping(path = CentralAuthResourceEndpoint.ROLE_V1_CREATE, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> create(@PathVariable("groupId") String groupId,
                                               @RequestBody RoleDTO roleDTO){
        RoleVO roleVO = roleService.create(groupId, roleDTO);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.created.successfully"), roleVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Update Role", response = ResponseBody.class)
    @PutMapping(path = CentralAuthResourceEndpoint.ROLE_V1_UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> update(@PathVariable("groupId") String groupId,
                                               @PathVariable("id") String id, @RequestBody RoleDTO roleDTO) {
        RoleVO roleVO = roleService.update(groupId, id, roleDTO);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.updated.successfully"), roleVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Delete Role", response = ResponseBody.class)
    @DeleteMapping(path = CentralAuthResourceEndpoint.ROLE_V1_DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> delete(@PathVariable("groupId") String groupId,
                                               @PathVariable("id") String id) {
        RoleVO roleVO = roleService.delete(groupId, id);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.deleted.successfully"), roleVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
