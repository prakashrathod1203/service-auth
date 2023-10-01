package in.om.controllers;

import in.om.component.ApiResponseDoc;
import in.om.component.Translator;
import in.om.constants.CentralAuthResourceEndpoint;
import in.om.dtos.UserDTO;
import in.om.response.ResponseBody;
import in.om.services.UserService;
import in.om.vos.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Prakash Rathod
 */
@Api(tags = "User", value = "User Controller")
@RestController
@RequestMapping(CentralAuthResourceEndpoint.USER)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @ApiResponseDoc
    @ApiOperation(value = "Create User", response = ResponseBody.class)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseBody> create(@RequestBody UserDTO userDTO){
        UserVO userVO = userService.create(userDTO);
        ResponseBody responseBody = new ResponseBody(Translator.toLocale("record.created.successfully"), userVO,true);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}
