package com.app.animeApplication.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * The persistent class for the genre database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="GENRE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long genreId;

	@Column(name="GENRE_NAME")
	private String genreName;

	//bi-directional many-to-many association to Movy
	@ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private List<Movies> movies;


}