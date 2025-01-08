package com.app.animeApplication.payloads;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtResponse {
	
	@NotNull
	private String jwt;
	
	private RefreshTokenDTO refreshToken;
	
	@NotNull
	private UserDTO user;
	
}
