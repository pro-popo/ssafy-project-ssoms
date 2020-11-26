package com.sdi.monitoring.controller.user;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdi.monitoring.domain.SuccessResponse;
import com.sdi.monitoring.model.user.dto.UserDTO;
import com.sdi.monitoring.model.user.dto.UserPrimitiveDTO;
import com.sdi.monitoring.model.user.dto.UserUpdateDTO;
import com.sdi.monitoring.model.user.service.UserService;

@RequestMapping("/user")
@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
//	@Autowired
//	private OracleSchedulingService oss;
	
	@GetMapping("/logout/{email}")
	public ResponseEntity logout(@PathVariable(name = "email") String email, HttpServletRequest httpServletRequest) {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
//		System.out.println("========== logout entered... ==========");
//		System.out.println("email : " + email);
		HttpSession httpSession = httpServletRequest.getSession();
		if (email.equals(httpSession.getAttribute("loginSession"))) {
			httpSession.setAttribute("loginSession", null);
		} else if (!email.equals((String) httpSession.getAttribute("loginSession"))) {
			result.status = true;
			result.result = "fail";
			response = new ResponseEntity<>(result, HttpStatus.OK);
			return response;
		}
//		oss.stop();
		result.status = true;
		result.result = "success";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/authorization/{email}")
	public ResponseEntity authorization(@PathVariable(name = "email") String email) {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean isAdmin = userService.isAdminCheck(email);
		result.status = true;
		result.result = isAdmin ? "yes" : "no";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	// dto null 반환될때 체크
	// logout 했는데 유저 프로필 정보 잘가져옴 - interceptor 를 안들어감
	@GetMapping("/profile/{email}")
	public ResponseEntity getUserProfile(@PathVariable(name = "email") String email) {
//		System.out.println("========== alluser entered... ==========");
//		System.out.println("email : " + email);
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		UserDTO userDTO = userService.getUserProfile(email);
		Map<String, Object> map = new HashMap<>();
		map.put("userProfile", userDTO);
		result.status = true;
		result.result = "success";
		result.map = map;
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	// 권한맞는지 check
	@PutMapping("/")
	public ResponseEntity updateUser(@RequestBody(required = true) UserUpdateDTO userUpdateDTO) {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean checkCanUpdateUserInfo = userService.updateUser(userUpdateDTO);
		result.status = true;
		result.result = checkCanUpdateUserInfo ? "success" : "fail";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	@PostMapping("/")
	public ResponseEntity deleteUser(@RequestBody(required = true) UserPrimitiveDTO userPrimitiveDTO) {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean isDelete = userService.deleteUser(userPrimitiveDTO);
		result.status = true;
		result.result = isDelete ? "success" : "fail";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
}
