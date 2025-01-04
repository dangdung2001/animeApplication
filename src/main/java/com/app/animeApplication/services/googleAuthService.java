package com.app.animeApplication.services;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.app.animeApplication.config.appConstants;
import com.app.animeApplication.entity.User;
import com.app.animeApplication.mapper.JwtResponseMapper;
import com.app.animeApplication.mapper.UserMapper;
import com.app.animeApplication.payloads.JwtResponse;
import com.app.animeApplication.payloads.registryDTO;
import com.app.animeApplication.provider.JWTprovider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class googleAuthService {

	private final UserService userService;
	private final AuthService authService;
	private final JWTprovider jwtProvider;
	private final UserMapper userMapper;
	private final JwtResponseMapper jwtRespMapper;

	public googleAuthService(UserService userService, AuthService authService, JWTprovider jwtProvider,
			UserMapper userMapper, JwtResponseMapper jwtRespMapper) {
		this.userService = userService;
		this.authService = authService;
		this.jwtProvider = jwtProvider;
		this.userMapper = userMapper;
		this.jwtRespMapper = jwtRespMapper;
	}

	public JwtResponse handlerLoginGoogle(String UserInfoJSON) throws JsonMappingException, JsonProcessingException {
		
		if (UserInfoJSON != null) {

			String decodedJson = URLDecoder.decode(UserInfoJSON, StandardCharsets.UTF_8);
			
			@SuppressWarnings("unchecked")
			Map<String, String> UserInfo = new ObjectMapper().readValue(decodedJson, Map.class);

			String randomPassword = UUID.randomUUID().toString();
			String hashedPassword = this.userService.generatePassword(randomPassword);

			registryDTO registryDTO = this.userMapper.ToRegistryDTO(UserInfo.get("email"), hashedPassword,
					UserInfo.get("avatar"));

			User user = this.userMapper.registryToEntity(registryDTO, appConstants.USER, 2);

			if(!isEmailExisted(user.getEmail())) {
				userService.registryUser(user);
			}

			Authentication auth = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());

			String jwt = this.jwtProvider.createToken(auth);

			if (jwt != null) {
				JwtResponse jwtResp = this.jwtRespMapper.toJwtResponse(jwt, user.getEmail());
				return jwtResp;
			}
		}
		return null;
	}
	
	
	private  boolean isEmailExisted(String email) {
		
		User user = (User) this.userService.loadUserByUsername(email);
		
		if(user != null) {
			return true;
		}
		return false;
	}
	
}





