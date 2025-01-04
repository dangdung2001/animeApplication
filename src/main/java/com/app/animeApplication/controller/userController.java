package com.app.animeApplication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.animeApplication.entity.User;
import com.app.animeApplication.exception.UserNotFoundException;
import com.app.animeApplication.payloads.UserDTO;
import com.app.animeApplication.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@RestController
@RequestMapping(value = "/api/users")
public class userController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(userController.class);
	
	private final UserService userService;
	
	
	public userController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/{userId}")
	public User HandlerGetUser(@PathVariable Long userId) {
		return this.userService.findById(userId);
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
