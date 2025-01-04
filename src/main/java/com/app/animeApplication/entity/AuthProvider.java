package com.app.animeApplication.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


/**
 * The persistent class for the auth_provider database table.
 * 
 */
@Entity
@Table(name="auth_provider")
@Data
public class AuthProvider implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="PROVIDER_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int providerId;

	@Column(name="PROVIDER_CODE")
	private String providerCode;

	@Column(name="PROVIDER_NAME")
	private String providerName;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="authProvider" , fetch = FetchType.LAZY)
	private List<User> users;

	
	

}