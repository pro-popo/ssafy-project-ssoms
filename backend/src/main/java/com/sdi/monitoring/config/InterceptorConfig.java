package com.sdi.monitoring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sdi.monitoring.interceptor.VerifyAdminInterceptor;
import com.sdi.monitoring.interceptor.VerifyUserInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Autowired
	private VerifyUserInterceptor verifyUserInterceptor;
	
	@Autowired
	private VerifyAdminInterceptor VerifyAdminInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(verifyUserInterceptor)
//			.addPathPatterns("/user", "/user/**");
//		
//		registry.addInterceptor(VerifyAdminInterceptor)
//			.addPathPatterns("/admin", "/admin/**");
		
	}
	
	
}
