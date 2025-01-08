package com.app.animeApplication.services;

import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.animeApplication.config.appConstants;
import com.app.animeApplication.entity.Role;
import com.app.animeApplication.entity.User;
import com.app.animeApplication.exception.UserNotFoundException;
import com.app.animeApplication.mapper.UserMapper;
import com.app.animeApplication.payloads.CustomUserDetails;
import com.app.animeApplication.payloads.UserDTO;
import com.app.animeApplication.reposiroty.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService implements UserDetailsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	private UserRepository userRepository;
	
	private UserMapper userMapper;

	public UserService(UserRepository userRepository, UserMapper userMapper) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
	}

	public User registryUser(User user) {

		return this.userRepository.save(user);
	}

	public UserDTO findById(Long userId) {
		
		User user = this.userRepository.findById(userId).orElseGet(null);
		if(user != null) {
			return this.userMapper.toUserDTO(user);
		}
		return null;
	}
	
	public UserDTO findByEmail(String email) {
		
		User user = this.userRepository.finduserByUsername(email);
		if(user != null) {
			return this.userMapper.toUserDTO(user);
		}
		return null;
	}

	public void deleteById(Long userId) {
		if (userId != null) {
			this.userRepository.deleteById(userId);
		}
	}

	public ResponseEntity<?> HandlerUpdateUser(UserDTO userDTO, HttpServletRequest request)
			throws UserNotFoundException {

		try {
			Authentication auth = (Authentication) request.getUserPrincipal();
			if (auth == null) {
				throw new IllegalArgumentException("User is not authenticated");
			}

			UserDetails authUser = (UserDetails) auth.getPrincipal();

			if (authUser == null || userDTO == null) {
				throw new IllegalArgumentException("Invalid user data");
			}

			if (!authUser.getUsername().equals(userDTO.getEmail().trim())) {
				throw new SecurityException("User is not authorized to update this information");
			}

			User userDB = userRepository.findById(userDTO.getUserid())
					.orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userDTO.getUserid()));

			if (!userDB.getEmail().equals(userDTO.getEmail())) {
				throw new SecurityException("Email mismatch between user and database record");
			}

			userDB.setAvatar(userDTO.getAvatar());
			userDB.setFullname(userDTO.getName());
			userRepository.save(userDB);

			return ResponseEntity.ok(userDTO);
		} catch (UserNotFoundException | SecurityException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = this.userRepository.finduserByUsername(email);

		CustomUserDetails userdetails = new CustomUserDetails();
		userdetails.setEmail(user.getEmail());
		userdetails.setPassword(user.getPassword());
		userdetails.setRoles(
				user.getRoles().stream().map(role -> "ROLE_" + role.getRoleName()).collect(Collectors.toList()));

		LOGGER.info("USER  : " + userdetails);
		return userdetails;
	}

	

}
