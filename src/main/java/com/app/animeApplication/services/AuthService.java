package com.app.animeApplication.services;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.app.animeApplication.config.appConstants;
import com.app.animeApplication.entity.Role;
import com.app.animeApplication.entity.User;
import com.app.animeApplication.mapper.JwtResponseMapper;
import com.app.animeApplication.mapper.UserMapper;
import com.app.animeApplication.payloads.JwtResponse;
import com.app.animeApplication.payloads.LoginCredentials;
import com.app.animeApplication.payloads.registryDTO;
import com.app.animeApplication.provider.JWTprovider;

@Component
public class AuthService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTprovider jwTprovider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private JwtResponseMapper jwtResponseMapper;
	
	

	public JwtResponse handlerLogin(LoginCredentials credential) {

		JwtResponse jwtResp = authenticateAndGenerateToken(credential.getEmail(), credential.getPassword());

		return jwtResp;

	}

	public JwtResponse handlerRegistry(registryDTO registryDTO) {
		User principal = this.userMapper.registryToEntity(registryDTO, appConstants.USER, 1);

		LOGGER.info("principal registry : " + principal);

		this.userService.registryUser(principal);

		Authentication auth = new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());

		String jwt = this.jwTprovider.createToken(auth);

		JwtResponse jwtResp = this.jwtResponseMapper.toJwtResponse(jwt, principal.getEmail());

		return jwtResp;
	}

	public JwtResponse authenticateAndGenerateToken(String email, String password) {

		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			
			 
			
			UserDetails user = (UserDetails)  authentication.getPrincipal();
//			
			LOGGER.info("USER : " + authentication.getPrincipal());
			Authentication auth = new UsernamePasswordAuthenticationToken(email, "" , user.getAuthorities());

			String jwt = jwTprovider.createToken(auth);
			if (jwt != null) {
				JwtResponse jwtResp = new JwtResponse();
				jwtResp.setJwt(jwt);
				jwtResp.setEmail(email);

				return jwtResp;
			}

		} catch (AuthenticationException e) {
			LOGGER.error("Authencation faild :" + e.getMessage());
		}

		return null;
	}

	

	

}
