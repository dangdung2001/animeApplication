package com.app.animeApplication.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Data
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROLE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int roleId;

	@Column(name="ROLE_NAME")
	private String roleName;

	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy = "roles" ,fetch = FetchType.LAZY)
	private List<User> users;

	
}