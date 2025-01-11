package com.app.animeApplication.services;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.app.animeApplication.config.appConstants;
import com.app.animeApplication.entity.RefreshToken;
import com.app.animeApplication.entity.User;
import com.app.animeApplication.entity.Verification_code;
import com.app.animeApplication.mapper.JwtResponseMapper;
import com.app.animeApplication.mapper.RefreshTokenMapper;
import com.app.animeApplication.mapper.UserMapper;
import com.app.animeApplication.payloads.JwtResponse;
import com.app.animeApplication.payloads.LoginCredentials;
import com.app.animeApplication.payloads.UserDTO;
import com.app.animeApplication.payloads.registryDTO;
import com.app.animeApplication.provider.JWTprovider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.mail.MessagingException;

@Component
public class AuthService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTprovider jwTprovider;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtResponseMapper jwtResponseMapper;

	@Autowired
	private RefreshTokenMapper refreshTokenMapper;

	@Autowired
	private Verify_codeService verify_codeService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private CaptchaService captchaService;

	public JwtResponse handlerLogin(LoginCredentials credential) {

		JwtResponse jwtResp = authenticateAndGenerateToken(credential.getEmail(), credential.getPassword(),
				credential.isRemember());

		return jwtResp;

	}

	public ResponseEntity<?> handleRegistry(registryDTO registryDTO) {
		Verification_code verificationCode = verify_codeService.getVerifyCodeByMail(registryDTO.getEmail());
		
		ResponseEntity<?> isVerifiCode = isVerificationCodeValid(verificationCode, registryDTO.getCode());
		
		if(isVerifiCode != null) {
			return isVerifiCode;
		}

		User userMap = mapRegistryToUser(registryDTO);

		User SavedUser = userService.registryUser(userMap);
		LOGGER.info("Principal registry: {}", SavedUser);

		return ResponseEntity.status(HttpStatus.CREATED).body(createJwtResponse(SavedUser));
	}

	public ResponseEntity<?> handleSendCodeMail(String mail) {

		UserDTO user = this.userService.findByEmail(mail);

		if (user != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Previously Registered Mail");
		}
		Verification_code verification_code = this.verify_codeService.getVerifyCodeByMail(mail);
		if (verification_code != null) {

			if (!LocalDateTime.now().isBefore(verification_code.getExpires_at())) {
				return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Code Has Been Sent");
			}
			
		}

		try {
			Long code = emailService.sendEmailMIME(mail, "Email Authentication", null);

			this.verify_codeService.saveVerifyCodeByMail(mail, String.valueOf(code));
			return ResponseEntity.status(HttpStatus.CREATED).body("Code Sended");
		} catch (MessagingException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Send Mail Faild");
		}

	}

	public ResponseEntity<?> handleVerifyCaptcha(String captchaToken) {

		if (this.captchaService.validateCaptcha(captchaToken)) {
			return ResponseEntity.ok("Captcha Is Valid");
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Captcha Invalid");
	}

	public JwtResponse handleRefreshToken(String AccessToken) {

		try {
			this.jwTprovider.validateToken(AccessToken);

			return buildJwtResponse(AccessToken);

		} catch (ExpiredJwtException e) {

			return handleExpiredToken(e);
		} catch (JwtException e) {
			LOGGER.error("Invalid JWT token: {}", e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("Error handling refresh token: {}", e.getMessage(), e);
		}
		return null;
	}

	private ResponseEntity<?> isVerificationCodeValid(Verification_code verificationCode, String providedCode) {
		if (verificationCode == null) {
			return ResponseEntity.badRequest().body("Verification code not found for the provided email.");
		}

		boolean isCodeExpired = verificationCode.getExpires_at().isBefore(LocalDateTime.now());
		if (isCodeExpired) {
			return ResponseEntity.badRequest().body("Verification code has expired.");
		}

		if(!verificationCode.getCode().equals(providedCode)) {
			return ResponseEntity.badRequest().body("Verification Code is not match.");
		}
		return null;
	}

	private JwtResponse authenticateAndGenerateToken(String email, String password, boolean remember) {

		try {
			// Xác thực người dùng
			Authentication authentication = authenticateUser(email, password);

			// Lấy thông tin người dùng và tạo access token
			UserDTO userDTO = userService.findByEmail(email);
			String jwt = jwTprovider.createAccessToken(createAuthentication(email, authentication));

			if (jwt == null) {
				LOGGER.warn("Failed to generate JWT token.");
				return null;
			}

			// Xây dựng JwtResponse
			JwtResponse jwtResponse = buildJwtResponse(jwt, userDTO, remember, authentication);
			return jwtResponse;

		} catch (AuthenticationException e) {
			LOGGER.error("Authentication failed: {}", e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Error during token generation: {}", e.getMessage(), e);
		}

		return null;
	}

	private Authentication authenticateUser(String email, String password) {
		return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
	}

	private Authentication createAuthentication(String email, Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return new UsernamePasswordAuthenticationToken(email, "", userDetails.getAuthorities());
	}

	private JwtResponse buildJwtResponse(String jwt, UserDTO userDTO, boolean remember, Authentication authentication) {
		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setJwt(jwt);
		jwtResponse.setUser(userDTO);

		// khi người dùng chọn lưu mật khẩu , tạo refresh token
		if (remember) {
			RefreshToken refreshToken = generateAndSaveRefreshToken(userDTO, authentication);
			jwtResponse.setRefreshToken(refreshTokenMapper.toRefreshTokenDTO(refreshToken));
		}

		return jwtResponse;
	}

	private User mapRegistryToUser(registryDTO registryDTO) {
		User user = userMapper.registryToEntity(registryDTO, appConstants.USER, 1);
		LOGGER.info("User registry: {}", user);
		return user;
	}

	private JwtResponse createJwtResponse(User user) {
		// Tạo access token
		Authentication auth = new UsernamePasswordAuthenticationToken(user.getEmail(), "", user.getAuthorities());
		String jwt = jwTprovider.createAccessToken(auth);

		// Xây dựng JwtResponse
		return jwtResponseMapper.toJwtResponse(jwt, userMapper.toUserDTO(user));
	}

	private RefreshToken generateAndSaveRefreshToken(UserDTO userDTO, Authentication authentication) {

		String refreshTokenStr = jwTprovider.createRefreshToken(authentication);

		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setID(userDTO.getUserid());
		refreshToken.setRefreshToken(refreshTokenStr);

//	    lưu refresh token
		refreshTokenService.saveRefreshToken(refreshToken);

		return refreshToken;
	}

	private JwtResponse buildJwtResponse(String token) {
		JwtResponse jwtResponse = new JwtResponse();
		jwtResponse.setJwt(token);
		return jwtResponse;
	}

	private JwtResponse handleExpiredToken(ExpiredJwtException e) {
		try {
			Claims claims = e.getClaims();
			UserDTO userDTO = userService.findByEmail(claims.getSubject());
			if (userDTO == null) {
				throw new IllegalArgumentException("User not found for email: " + claims.getSubject());
			}

			// Extract authorities
			List<GrantedAuthority> authorities = extractAuthorities(claims);
			Authentication auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);

			// Validate refresh token
			RefreshToken refreshToken = refreshTokenService.findRefreshTokenByID(userDTO.getUserid());
			jwTprovider.validateToken(refreshToken.getRefreshToken());

			// Generate new access token
			String newAccessToken = jwTprovider.createAccessToken(auth);
			return jwtResponseMapper.toJwtResponse(newAccessToken, userDTO);
		} catch (Exception e2) {
			LOGGER.error("Error handling refresh token: {}", e2.getMessage(), e2);
			return null;
		}

	}

	private List<GrantedAuthority> extractAuthorities(Claims claims) {
		String authorStr = (String) claims.get("auth");
		return Arrays.stream(authorStr.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

}
