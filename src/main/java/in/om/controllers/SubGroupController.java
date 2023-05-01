package in.om.controllers;

import in.om.component.ApiResponseDoc;
import in.om.component.Translator;
import in.om.constants.CentralAuthResourceEndpoint;
import in.om.dtos.SubGroupDTO;
import in.om.response.ResponseBody;
import in.om.services.SubGroupService;
import in.om.vos.SubGroupVO;
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
@Api(tags = "SubGroup", value = "SubGroup Controller")
@RestController
@RequestMapping(CentralAuthResourceEndpoint.SUB_GROUP)
@RequiredArgsConstructor
public class SubGroupController {

    private final SubGroupService subGroupService;

    @ApiResponseDoc
    @ApiOperation(value = "Fetch SubGroup", response = ResponseBody.class)
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> fetchSubGroup(@PathVariable("groupId") String groupId,
                                                   @PathVariable("id") String id) {
        SubGroupVO subGroupVO = subGroupService.fetchSubGroup(groupId, id);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.fetch.successfully"), subGroupVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Fetch SubGroups", response = ResponseBody.class)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> fetchGroups(@PathVariable("groupId") String groupId) {
        List<SubGroupVO> subGroupVOList = subGroupService.fetchSubGroups(groupId);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.fetch.successfully"), subGroupVOList,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Create SubGroup", response = ResponseBody.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> create(@PathVariable("groupId") String groupId,
                                               @RequestBody SubGroupDTO subGroupDTO){
        SubGroupVO subGroupVO = subGroupService.create(groupId, subGroupDTO);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.created.successfully"), subGroupVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Update SubGroup", response = ResponseBody.class)
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> update(@PathVariable("groupId") String groupId,
                                               @PathVariable("id") String id, @RequestBody SubGroupDTO subGroupDTO) {
        SubGroupVO subGroupVO = subGroupService.update(groupId, id, subGroupDTO);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.updated.successfully"), subGroupVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @ApiResponseDoc
    @ApiOperation(value = "Delete SubGroup", response = ResponseBody.class)
    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> delete(@PathVariable("groupId") String groupId,
                                               @PathVariable("id") String id) {
        SubGroupVO subGroupVO = subGroupService.delete(groupId, id);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.deleted.successfully"), subGroupVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
