package com.sdi.monitoring.controller.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.sdi.monitoring.util.AES256Cipher;

@RequestMapping("/nonuser")
@RestController
public class NonUserController {
	@Autowired
	private NonUserService nonUserService;
	
	@GetMapping("/existence/admin")
	public ResponseEntity authorization() {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean isAdmin = nonUserService.hasAdmin();
		result.status = true;
		result.result = isAdmin ? "yes" : "no";
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	@PutMapping("/signup")
	public ResponseEntity signUp(@RequestBody UserSignUpDTO userSignUpDTO) {
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

	@PostMapping("/signin")
	public ResponseEntity login(@RequestBody(required = true) UserPrimitiveDTO userPrimitiveDTO,
			HttpSession httpSession) {
		ResponseEntity response = null;
		final SuccessResponse result = new SuccessResponse();
		boolean checkCanLogin = nonUserService.signIn(userPrimitiveDTO);
		result.status = true;
		if (checkCanLogin) {
			result.result = "success";
			httpSession.setAttribute("loginSession", userPrimitiveDTO.getEmail());
		} else {
			result.result = "fail";
		}
		response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}
}