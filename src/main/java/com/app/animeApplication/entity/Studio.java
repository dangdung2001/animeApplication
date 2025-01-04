package com.app.animeApplication.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * The persistent class for the studio database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Studio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="STUDIO_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studioId;

	@Column(name="STUDIO_NAME")
	private String studioName;

	//bi-directional many-to-one association to MovieStudio
	@ManyToMany( mappedBy = "studios",fetch = FetchType.LAZY, cascade = { CascadeType.MERGE})
	private List<Movies> movies;

	
	
}