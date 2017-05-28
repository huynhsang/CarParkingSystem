package com.se52.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;



public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter{
	
	public AjaxLoginProcessingFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		//System.out.println(SecurityContextHolder.getContext().getAuthentication());
		if(SecurityContextHolder.getContext().getAuthentication() != null){
			response.sendRedirect(redirect(request));
			return null;
		}
		
		response.sendRedirect("/index");
		return null;
	}
	
	private static String redirect(HttpServletRequest request){
		SecurityContextHolderAwareRequestWrapper SCHARW = new SecurityContextHolderAwareRequestWrapper(request, "ROLE");
		if(SCHARW.isUserInRole("ROLE_ADMIN")){
			return "/admin";
		}
		if(SCHARW.isUserInRole("ROLE_USER")){
			return "/user";
		}
		return "/staff";
	}
}
