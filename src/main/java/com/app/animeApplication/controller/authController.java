package com.app.animeApplication.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.animeApplication.entity.RefreshToken;
import com.app.animeApplication.payloads.ErrorResponse;
import com.app.animeApplication.payloads.JwtResponse;
import com.app.animeApplication.payloads.LoginCredentials;
import com.app.animeApplication.payloads.registryDTO;
import com.app.animeApplication.services.AuthService;
import com.app.animeApplication.services.EmailService;
import com.app.animeApplication.services.googleAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/public")
@Tag(name = "AUTHENTICATION", description = "APIs for managing authentication")
public class authController {

	private static final Logger LOGGER = LoggerFactory.getLogger(authController.class);

	private final AuthService authService;

	private final googleAuthService googleAuthService;

	private final EmailService emailService;

	public authController(AuthService authService, googleAuthService googleAuthService, EmailService emailService) {
		this.authService = authService;
		this.googleAuthService = googleAuthService;
		this.emailService = emailService;
	}

	@PostMapping("/login")
	public ResponseEntity<?> HandlerLogin(@RequestHeader(value = "User-Agent") String userAgent,
			@Valid @RequestBody LoginCredentials credential) {

		JwtResponse jwtResp = authService.handlerLogin(credential);

		if (jwtResp != null) {
			LOGGER.info("userAgent : " + userAgent);
			return ResponseEntity.ok(jwtResp);
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
	}

	@GetMapping("/login/google")
	public ResponseEntity<?> HandlerLoginGoogle(@RequestParam("AuthenticationJSON") String UserInfoJSON)
			throws JsonMappingException, JsonProcessingException {

		JwtResponse jwtResp = this.googleAuthService.handlerLoginGoogle(UserInfoJSON);

		if (jwtResp != null) {
			return ResponseEntity.ok(jwtResp);
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("loggin google faild!");
	}

	@PostMapping("/register")
	public ResponseEntity<?> HandlerRegistry(@Valid @RequestBody registryDTO registryDTO) {

		JwtResponse jwtResp = this.authService.handlerRegistry(registryDTO);

		if (jwtResp != null) {

			return ResponseEntity.ok(jwtResp);
		}
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("registry faild!");
	}

	@GetMapping("/email/send/{email}")
	public ResponseEntity<?> sendEmail(@Valid @PathVariable String email) throws MessagingException {

		try {
			Long code = emailService.sendEmailMIME(email, "Email Authentication", null);

			return ResponseEntity.ok(code);

		} catch (Exception e) {

			LOGGER.error("Send mail : " + e.getMessage());

		}

		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Send Code MAil Faild!");
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
		String AccessToken = request.get("AccessToken");

		if (AccessToken == null || AccessToken.isEmpty()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old Access token is required");
		}

		JwtResponse jwtResponse = authService.handleRefreshToken(AccessToken);

		if (jwtResponse != null) {
			return ResponseEntity.ok(jwtResponse);
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid refresh token");
	}

}
