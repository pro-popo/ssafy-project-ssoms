package com.sdi.monitoring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sdi.monitoring.exception.BadRequestException;
import com.sdi.monitoring.exception.UnAuthorizationException;
import com.sdi.monitoring.model.user.entity.UserEntity;
import com.sdi.monitoring.model.user.repository.UserMongoRepo;

@Component
public class VerifyAdminInterceptor implements HandlerInterceptor {
	
	@Autowired
	private UserMongoRepo userMongoRepo;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		System.out.println("========== VerifyAdminInterceptor entered... ==========");

		HttpSession httpSession = request.getSession();
		String sessionEmail = (String)httpSession.getAttribute("loginSession");
		
		if(sessionEmail == null || sessionEmail.equals("")) 
			throw new UnAuthorizationException("VerifyAdminInterceptor throw Exception(UnAuthorizationException exception)\n" + "dont have session info");
		
		UserEntity userEntity = userMongoRepo.findUserByEmail(sessionEmail);
		if(userEntity == null)
			throw new BadRequestException("VerifyAdminInterceptor throw Exception(BadRequestException exception)\n" + "This User is not found : " + sessionEmail);
			
		if(!userEntity.getInfo().isAdmin())
//			throw new UnAuthorizationException("VerifyAdminInterceptor throw Exception(UnAuthorizationException exception)\n" + "dont have session info");
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		
		return true;
	}
}
