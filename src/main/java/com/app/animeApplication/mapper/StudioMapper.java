package com.app.animeApplication.mapper;

import org.springframework.stereotype.Component;

import com.app.animeApplication.entity.Studio;
import com.app.animeApplication.payloads.JwtResponse;
import com.app.animeApplication.payloads.StudioDTO;

@Component
public class StudioMapper {

	public JwtResponse toJwtResponse(String jwt, String email) {
		
		JwtResponse jwtresp = new JwtResponse();
		jwtresp.setEmail(email);
		jwtresp.setJwt(jwt);
		
		return jwtresp;
	}

	public Studio toStudioEntity(StudioDTO studioDTO) {
		
		Studio studio = new Studio();
		
		studio.setStudioName(studioDTO.getStudioName());
		
		return studio;
	}
}
