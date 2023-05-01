package in.om.controllers;

import in.om.component.ApiResponseDoc;
import in.om.component.Translator;
import in.om.constants.CentralAuthResourceEndpoint;
import in.om.dtos.GroupDTO;
import in.om.response.ResponseBody;
import in.om.services.GroupService;
import in.om.vos.GroupVO;
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
@Api(tags = "Group", value = "Group Controller")
@RestController
@RequestMapping(CentralAuthResourceEndpoint.GROUP)
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @ApiResponseDoc
    @ApiOperation(value = "Fetch Group", response = ResponseBody.class)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> fetchGroup(@PathVariable("organizationId") String organizationId,
                                                   @PathVariable("id") String id) {
        GroupVO groupVO = groupService.fetchGroup(organizationId, id);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.fetch.successfully"), groupVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Fetch Groups", response = ResponseBody.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> fetchGroups(@PathVariable("organizationId") String organizationId) {
        List<GroupVO> groupVOList = groupService.fetchGroups(organizationId);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.fetch.successfully"), groupVOList,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Create Group", response = ResponseBody.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> create(@PathVariable("organizationId") String organizationId,
                                               @RequestBody GroupDTO groupDTO){
        GroupVO groupVO = groupService.create(organizationId, groupDTO);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.created.successfully"), groupVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Update Group", response = ResponseBody.class)
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> update(@PathVariable("organizationId") String organizationId,
                                               @PathVariable("id") String id, @RequestBody GroupDTO groupDTO) {
        GroupVO groupVO = groupService.update(organizationId, id, groupDTO);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.updated.successfully"), groupVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Delete Group", response = ResponseBody.class)
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> delete(@PathVariable("organizationId") String organizationId,
                                               @PathVariable("id") String id) {
        GroupVO groupVO = groupService.delete(organizationId, id);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.deleted.successfully"), groupVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
