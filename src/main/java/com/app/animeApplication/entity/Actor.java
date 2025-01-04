package com.app.animeApplication.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * The persistent class for the actors database table.
 * 
 */
@Entity
@Table(name="actors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ACTORS_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long actorsId;

	@Column(name="ACTORS_NAME")
	private String actorsName;

	//bi-directional many-to-many association to Movy
	@ManyToMany(mappedBy="actors",fetch = FetchType.LAZY, cascade = { CascadeType.MERGE})	
	private List<Movies> movies;

	

}