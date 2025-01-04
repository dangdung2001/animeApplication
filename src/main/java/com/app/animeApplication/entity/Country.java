package com.app.animeApplication.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * The persistent class for the country database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COUNTRY_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long countryId;

	@Column(name="COUNTRY_NAME")
	private String countryName;

	//bi-directional many-to-many association to Movy
	@ManyToMany(mappedBy = "countries",fetch = FetchType.LAZY, cascade = { CascadeType.MERGE})
	private List<Movies> movies;
	

	
}