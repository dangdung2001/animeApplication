package com.app.animeApplication.config;

import com.app.animeApplication.services.EmailService;

public class appConstants {

//	url

	public static final String[] PUBLIC_URLS = { "/", "/api/public/**", "/login/oauth2/code/google",
			"/v3/api-docs/**", "/swagger-ui/**" };

	public static final String[] USER_URLS = { "/api/users/**" };

	public static final String[] ADMIN_URLS = { "/api/admin/**" };

//	public static final String LOGIN_URL = "/api/public/login";

//	roles

	public static final String USER = "USER";

	public static final String ADMIN = "ADMIN";

	

}
