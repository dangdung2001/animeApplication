package com.app.animeApplication.payloads;

import javax.validation.constraints.Email;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginCredentials {
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	private String password;
}
