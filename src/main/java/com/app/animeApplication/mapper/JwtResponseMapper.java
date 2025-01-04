package com.app.animeApplication.mapper;

import org.springframework.stereotype.Component;

import com.app.animeApplication.payloads.JwtResponse;

@Component
public class JwtResponseMapper {

	public JwtResponse toJwtResponse(String jwt, String email) {
		
		JwtResponse jwtresp = new JwtResponse();
		jwtresp.setEmail(email);
		jwtresp.setJwt(jwt);
		
		return jwtresp;
	}
}
