package com.app.animeApplication.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "verification_codes")
@Data
public class Verification_code {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String mail;
	
	private String code;
	
	private String status;
	
	private LocalDateTime created_at;
	
	private LocalDateTime expires_at;
	
}
