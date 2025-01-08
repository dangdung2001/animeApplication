package com.app.animeApplication.mapper;

import org.springframework.stereotype.Component;

import com.app.animeApplication.payloads.JwtResponse;
import com.app.animeApplication.payloads.UserDTO;

@Component
public class JwtResponseMapper {

	public JwtResponse toJwtResponse(String jwt, UserDTO userDTO) {
		
		JwtResponse jwtresp = new JwtResponse();
		jwtresp.setUser(userDTO);
		jwtresp.setJwt(jwt);
		
		return jwtresp;
	}
}
