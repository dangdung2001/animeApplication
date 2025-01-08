package com.app.animeApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.animeApplication.entity.RefreshToken;
import com.app.animeApplication.reposiroty.RefreshTokenRepository;

@Service
public class RefreshTokenService {

	
	@Autowired
	private RefreshTokenRepository refreshTokenRepository;
	
	
	public void saveRefreshToken(RefreshToken refreshToken) {
		
		this.refreshTokenRepository.save(refreshToken);
	}


	public RefreshToken findRefreshTokenByID(Long userid) {
		
		return this.refreshTokenRepository.findById(userid).orElseGet(null);
	}
}
