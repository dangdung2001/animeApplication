package com.app.animeApplication.payloads;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class registryDTO {

	@NotNull
	private String email;
	
	@NotNull
	private String password;
	
	private String name;
	
	private String avatar;
	
}
