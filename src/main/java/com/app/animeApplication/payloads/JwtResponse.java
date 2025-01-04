package com.app.animeApplication.payloads;

import javax.validation.constraints.Email;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JwtResponse {
	
	@NotNull
	private String jwt;
	
	@Email
	private String email;
}
