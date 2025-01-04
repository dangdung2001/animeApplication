package com.app.animeApplication.payloads;

import lombok.Data;

@Data
public class ErrorResponse {
	
	private String ErrorName;
	private String httpstatus;
	private String ms;
}
