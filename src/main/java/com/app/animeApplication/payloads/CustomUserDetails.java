package com.app.animeApplication.payloads;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class CustomUserDetails implements UserDetails, Serializable{

	
	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	private List<String> roles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<? extends GrantedAuthority> authors = this.roles.stream().map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
		return authors;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return email;
	}

}
