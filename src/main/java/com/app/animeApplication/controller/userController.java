package com.app.animeApplication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.animeApplication.entity.User;
import com.app.animeApplication.exception.UserNotFoundException;
import com.app.animeApplication.mapper.UserMapper;
import com.app.animeApplication.payloads.UserDTO;
import com.app.animeApplication.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/users")
public class userController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(userController.class);
	
	private final UserService userService;
	
	private final UserMapper userMapper;
	
	
	public userController(UserService userService, UserMapper userMapper) {
		this.userService = userService;
		this.userMapper = userMapper;
	}

	@GetMapping("/{userId}")
	public UserDTO HandlerGetUserByID(@PathVariable Long userId) {
		
		return this.userService.findById(userId);
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> HandlerGetUser(HttpServletRequest request) {
		
		Authentication auth = (Authentication) request.getUserPrincipal();
		
		if(auth != null && auth.getName() != null) {
			UserDTO userdto = this.userService.findByEmail(auth.getName());
			return ResponseEntity.ok(userdto);
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Authentication User Invalid");
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> HandlerUpdateUser(HttpServletRequest request ,  @Valid @RequestBody UserDTO userDTO) throws UserNotFoundException {
		
		return this.userService.HandlerUpdateUser(userDTO, request);
	}


	@DeleteMapping("/delete/{userId}")
	public void HandlerDeleteUser(@PathVariable Long userId) {
		this.userService.deleteById(userId);
	}
}
