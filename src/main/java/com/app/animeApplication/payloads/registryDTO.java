package com.app.animeApplication.payloads;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class registryDTO {

	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	@NotNull
	private String name;
	
	@NotNull
	private String avatar;
	
	@NotNull
	private String code;
	
	
}
