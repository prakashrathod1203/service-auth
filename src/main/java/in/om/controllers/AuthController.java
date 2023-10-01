package in.om.controllers;

import in.om.component.ApiResponseDoc;
import in.om.component.Translator;
import in.om.constants.CentralAuthResourceEndpoint;
import in.om.payload.*;
import in.om.request.UserLoginRequest;
import in.om.response.ResponseBody;
import in.om.response.UserLoginResponse;
import in.om.security.JwtUtil;
import in.om.security.UserPrincipal;
import in.om.services.OLDUserService;
import in.om.component.Notification;
import in.om.utility.ApplicationConstants;
import in.om.vos.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;

@Api(tags = "Authentication", value = "AuthController")
@Validated
@RestController
@RequestMapping(CentralAuthResourceEndpoint.AUTH)
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;
//	private final OLDUserService userService;
    private final JwtUtil jwtUtil;
//    private final PasswordEncoder passwordEncoder;
//    private final Notification userNotificationService;

    @ApiOperation(value = "Login", response = LoginRequest.class)
    @PostMapping(path = CentralAuthResourceEndpoint.AUTH_LOGIN)
    public ResponseEntity<ResponseBody> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse, HttpServletRequest httpServletRequest) {
		Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtUtil.generateToken(userPrincipal);
		LoginVO loginVO = LoginVO.builder().token(token).resource(userPrincipal.getResource())
				.build();
		ResponseBody responseBody = new ResponseBody(Translator.toLocale("user.login.success"), loginVO,true);
        return ResponseEntity.ok(responseBody);
    }

//    @ApiOperation(value = "Forgot Password", response = ApiResponse.class)
//	@PostMapping("/forgot")
//	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
//		Optional<OLDUser> userObj = userService.findByUserName(forgotPasswordRequest.getUserName());
//		String resetToken = UUID.randomUUID().toString();
//		if(userObj.isPresent()) {
//			SecurityContextHolder.getContext().setAuthentication(null);
//			OLDUser user = userObj.get();
//			user.setPassword(passwordEncoder.encode(resetToken));
//			userService.update(user);
//			userNotificationService.sendChangePasswordMail(forgotPasswordRequest.getUserName());
//			return ResponseEntity.ok(new ApiResponse(true,ApplicationConstants.UPDATED_KEY,Translator.toLocale("user.forgot.password.request"), HttpStatus.OK.value(), null));
//		} else {
//			return ResponseEntity.ok(new ApiResponse(true,ApplicationConstants.NOT_FOUND_KEY,Translator.toLocale("user.not.found"), HttpStatus.NOT_FOUND.value(), null));
//		}
//	}
//
//	@ApiOperation(value = "Change Password", response = ApiResponse.class)
//	@PostMapping("/change")
//	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, @ApiIgnore @RequestHeader(value=ApplicationConstants.HEADER_STRING) String jwtToken) {
//		if(changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
//			jwtToken = jwtToken.replace(ApplicationConstants.TOKEN_PREFIX, "").trim();
//			TokenDetails tokenDetails = jwtUtil.getTokenDetails(jwtToken);
//			Optional<OLDUser> userObj = userService.findByUserName(tokenDetails.getUserName());
//			if(userObj.isPresent()) {
//				OLDUser user = userObj.get();
//				user.setPassword(passwordEncoder.encode(changePasswordRequest.getConfirmPassword()));
//				userService.update(user);
//				return ResponseEntity.ok(new ApiResponse(true,ApplicationConstants.UPDATED_KEY,Translator.toLocale("user.password.changed.success"), HttpStatus.OK.value(), null));
//			} else {
//				return ResponseEntity.ok(new ApiResponse(true,ApplicationConstants.NOT_FOUND_KEY,Translator.toLocale("user.not.found"), HttpStatus.NOT_FOUND.value(), null));
//			}
//		} else {
//			return ResponseEntity.ok(new ApiResponse(true,ApplicationConstants.VALIDATION_FAILED_KEY,Translator.toLocale("user.password.not.match"), HttpStatus.FORBIDDEN.value(), null));
//		}
//	}
//
//	@ApiOperation(value = "Verify Password", response = ApiResponse.class)
//	@PostMapping("/verify")
//	public ResponseEntity<?> verifyPassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest, @ApiIgnore @RequestHeader(value=ApplicationConstants.HEADER_STRING) String jwtToken) {
//		if(changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
//			jwtToken = jwtToken.replace(ApplicationConstants.TOKEN_PREFIX, "").trim();
//			TokenDetails tokenDetails = jwtUtil.getTokenDetails(jwtToken);
//			Optional<OLDUser> userObj = userService.findByUserName(tokenDetails.getUserName());
//			if(userObj.isPresent()) {
//				OLDUser user = userObj.get();
//				if(passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPassword())) {
//					return ResponseEntity.ok(new ApiResponse(true,ApplicationConstants.GET_KEY,Translator.toLocale("user.password.correct.password"), HttpStatus.OK.value(), Boolean.TRUE));
//				} else {
//					return ResponseEntity.ok(new ApiResponse(false,ApplicationConstants.GET_KEY,Translator.toLocale("user.password.wrong.password"), HttpStatus.OK.value(), Boolean.FALSE));
//				}
//			} else {
//				return ResponseEntity.ok(new ApiResponse(true,ApplicationConstants.NOT_FOUND_KEY,Translator.toLocale("user.not.found"), HttpStatus.NOT_FOUND.value(), null));
//			}
//		} else {
//			return ResponseEntity.ok(new ApiResponse(true,ApplicationConstants.VALIDATION_FAILED_KEY,Translator.toLocale("user.password.not.match"), HttpStatus.FORBIDDEN.value(), null));
//		}
//	}
}
