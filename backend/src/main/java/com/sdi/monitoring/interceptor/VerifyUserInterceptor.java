//package com.sdi.monitoring.interceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import com.sdi.monitoring.exception.BadRequestException;
//import com.sdi.monitoring.exception.UnAuthorizationException;
//
//@Component
//public class VerifyUserInterceptor implements HandlerInterceptor {
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		System.out.println("========== VerifyUserInterceptor entered... ==========");
//		HttpSession httpSession = request.getSession();
//		String sessionEmail = (String)httpSession.getAttribute("loginSession");
//		
//		if(sessionEmail == null || sessionEmail.equals("")) 
//			throw new UnAuthorizationException("VerifyUserInterceptor throw Exception(UnAuthorizationException)\n" + "dont have session info");
//		return true;
//	}
//}
