package com.app.animeApplication.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.animeApplication.entity.RefreshToken;
import com.app.animeApplication.payloads.RefreshTokenDTO;

@Component
public class RefreshTokenMapper {
	
	
	public RefreshTokenDTO toRefreshTokenDTO(RefreshToken refreshToken) {
		
		RefreshTokenDTO refreshTokenDTO =  new RefreshTokenDTO();
		
		refreshTokenDTO.setID(refreshToken.getID());
		refreshTokenDTO.setToken(refreshToken.getRefreshToken());
		
		return refreshTokenDTO;

	}
}
