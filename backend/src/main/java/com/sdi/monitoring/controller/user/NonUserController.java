package com.sdi.monitoring.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sdi.monitoring.domain.SuccessResponse;
import com.sdi.monitoring.model.user.dto.UserPrimitiveDTO;
import com.sdi.monitoring.model.user.dto.UserSignUpDTO;
import com.sdi.monitoring.model.user.service.NonUserService;

@CrossOrigin("*")
@RequestMapping("/nonuser")
@RestController
public class NonUserController {
	@Autowired
	private NonUserService nonUserService;
//	
//	@Autowired
//	private OracleSchedulingService oss;
	
	@GetMapping("/existence/admin")
	public ResponseEntity authorization() {
//		System.out.println("========== authorization entered... ==========");
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean isAdmin = nonUserService.hasAdmin();
		result.status = true;
		System.out.println(isAdmin);
		result.result = isAdmin ? "yes" : "no";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	@PutMapping("/signup")
	public ResponseEntity signUp(@RequestBody UserSignUpDTO userSignUpDTO) {
//		System.out.println("========== signUp entered... ==========");
		System.out.println(userSignUpDTO.toString());
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean checkCanUseEmail = nonUserService.emailDuplicateCheckForSignUp(userSignUpDTO.getEmail());
		if (!checkCanUseEmail) {
			result.status = true;
			result.result = "fail";
			response = new ResponseEntity<>(result, HttpStatus.OK);
			return response;
		} else if (checkCanUseEmail) {
			nonUserService.signUp(userSignUpDTO);
			result.status = true;
			result.result = "success";
			response = new ResponseEntity<>(result, HttpStatus.OK);
		}
		return response;
	}

	// Session 처리해주는 부분 추가
	@PostMapping("/signin")
	public ResponseEntity login(@RequestBody(required = true) UserPrimitiveDTO userPrimitiveDTO,
			HttpSession httpSession) {
//		System.out.println("========== login entered... ==========");
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean checkCanLogin = nonUserService.signIn(userPrimitiveDTO);
		result.status = true;
		if (checkCanLogin) {
//			oss.start();
			result.result = "success";
			httpSession.setAttribute("loginSession", userPrimitiveDTO.getEmail());
		} else {
			result.result = "fail";
		}
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
}