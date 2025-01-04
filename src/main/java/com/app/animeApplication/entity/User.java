package com.app.animeApplication.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@Data
public class User implements  UserDetails, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userid;

	
	private String avatar;
	
	@Column(name = "name")
	@NotNull
	private String fullname;
	
	@NotNull
	private LocalDate creationdate;

	@NotNull
	private String email;

	
	@NotNull
	private LocalDate lastlogin;

	@NotNull
	private String password;

	//bi-directional many-to-one association to Comment
	@OneToMany(mappedBy="user" , fetch = FetchType.LAZY)
	private List<Comment> comments;

	//bi-directional many-to-one association to History
	@OneToMany(mappedBy="user" , fetch = FetchType.LAZY)
	private List<History> histories;

	//bi-directional many-to-one association to Rating
	@OneToMany(mappedBy="user" , fetch = FetchType.LAZY)
	private List<Rating> ratings;

	//bi-directional many-to-many association to Role
	@ManyToMany()
	@JoinTable(
		    name = "user_role", 
		    joinColumns = @JoinColumn(name = "USERID"), 
		    inverseJoinColumns = @JoinColumn(name = "ROLEID") 
		)
	private List<Role> roles;

	//bi-directional many-to-one association to AuthProvider
	@ManyToOne
	@JoinColumn(name="AUTH_PROVIDER_ID")
	private AuthProvider authProvider;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<? extends GrantedAuthority> authors = this.roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
				.collect(Collectors.toList());
		
		return authors;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	
}