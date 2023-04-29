package in.om.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import in.om.component.Translator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.om.model.Role;
import in.om.payload.ApiResponse;
import in.om.services.RoleService;
import in.om.utility.ApplicationConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(ApplicationConstants.USER_API_PRIFIX + "/role")
@Api(tags = "Role", value = "/role")
public class RoleController {

	private final RoleService roleService;
	
	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@ApiOperation(value = "Save or Update Role", response = Role.class)
    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody Role role) {
		Optional<Role> optional = roleService.findByRoleName(role.getName());
		if(optional.isPresent() & role.getRoleId() == 0){
			return ResponseEntity.ok(new ApiResponse(false, ApplicationConstants.EXIST_KEY, Translator.toLocale("role.exist"), HttpStatus.FOUND.value(), null));
		}else{
			if(optional.isPresent()){
				role.setName(optional.get().getName());
			}
			Role result = roleService.update(role);
			String msg = role.getRoleId() <= 0 ? ApplicationConstants.ADDED_KEY : ApplicationConstants.UPDATED_KEY;
			String description = role.getRoleId() <= 0 ? Translator.toLocale("role.added.success") : Translator.toLocale("role.updated.success");
			return ResponseEntity.ok(new ApiResponse(true, msg, description  , HttpStatus.CREATED.value(), result));
		}
    }
	
	@ApiOperation(value = "Delete Role", response = Role.class)
	@DeleteMapping("/delete/{roleId}")
	public ResponseEntity<?> delete(@PathVariable Short roleId){
		Optional<Role> optional = roleService.findById(roleId);
		if(optional.isPresent()){
			roleService.delete(optional.get());
			return ResponseEntity.ok(new ApiResponse(true, ApplicationConstants.DELETED_KEY, Translator.toLocale("role.deleted.success"), HttpStatus.OK.value(), optional.get()));
		}else{
			return ResponseEntity.ok(new ApiResponse(false, ApplicationConstants.NOT_FOUND_KEY, Translator.toLocale("role.not.exist"), HttpStatus.NOT_FOUND.value(), null));
		}
	}
	
	@ApiOperation(value = "Check Role Exist Or Not", response = ApiResponse.class)
	@GetMapping("/{roleName}")
	public ResponseEntity<?> get(@PathVariable String roleName){
		Optional<Role> optional = roleService.findByRoleName(roleName);
		if(optional.isPresent()){
			List<Role> role = new ArrayList<Role>();
			role.add(optional.get());
			return ResponseEntity.ok(new ApiResponse(true, ApplicationConstants.EXIST_KEY, Translator.toLocale("role.view"), HttpStatus.FOUND.value(), role));
		} else {
			return ResponseEntity.ok(new ApiResponse(true, ApplicationConstants.NOT_FOUND_KEY, Translator.toLocale("role.not.exist"), HttpStatus.NOT_FOUND.value(), null));
		}
	}
	
	@ApiOperation(value = "Get Role", response = ApiResponse.class)
	@GetMapping("/check/{roleName}")
	public ResponseEntity<?> check(@PathVariable String roleName){
		Optional<Role> optional = roleService.findByRoleName(roleName);
		Map<String, Boolean> map = new HashMap<String, Boolean>();
		if(optional.isPresent()){
			map.put(ApplicationConstants.IS_EXIST, Boolean.TRUE);
			return ResponseEntity.ok(new ApiResponse(true, ApplicationConstants.EXIST_KEY, Translator.toLocale("role.exist"), HttpStatus.FOUND.value(), map));
		} else {
			map.put(ApplicationConstants.IS_EXIST, Boolean.FALSE);
			return ResponseEntity.ok(new ApiResponse(true, ApplicationConstants.NOT_FOUND_KEY, Translator.toLocale("role.not.exist"), HttpStatus.NOT_FOUND.value(), map));
		}
	}
	
	@ApiOperation(value = "Get All Roles", response = ApiResponse.class)
	@GetMapping("all")
	public ResponseEntity<?> getAll() {
		List<Role> roles = roleService.findAll();
		return ResponseEntity.ok(new ApiResponse(true, ApplicationConstants.GET_KEY, Translator.toLocale("role.get.all"), HttpStatus.OK.value(), roles));
	}
}
