package com.app.animeApplication.provider;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class customAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	private static final Logger LOGGER = LoggerFactory.getLogger(customAuthenticationSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
		
		
		try {
			if(token != null) {
				OAuth2User user = token.getPrincipal();
				LOGGER.info("token : " + token);
				LOGGER.info("user : " + user);
				
				Map<String, String> userInfo = new HashMap<String, String>();
				
				String email = user.getAttribute("email");
				String name = user.getAttribute("name");
				String avatar = user.getAttribute("picture");
				
				userInfo.put("email", email);
				userInfo.put("name", name);
				userInfo.put("avatar", avatar);
				
				String json = URLEncoder.encode(new ObjectMapper().writeValueAsString(userInfo), "UTF-8");
		
				response.sendRedirect("/api/public/login/google?AuthenticationJSON=" + json);
			}
		} catch (Exception e) {
			LOGGER.info("" + e);
			LOGGER.info("fail in handler authentication");
			
		}
		
	}
	
	

}
