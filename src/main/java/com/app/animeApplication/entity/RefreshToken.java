package com.app.animeApplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "REFRESH_TOKEN")
@Data
public class RefreshToken {
		
	@Id
	@Column(name = "USER_ID")
	private Long ID;
	
	@Column(name="REFRESH_TOKEN")
	private String refreshToken;
	
	
}
