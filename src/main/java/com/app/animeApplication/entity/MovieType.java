package com.app.animeApplication.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * The persistent class for the movie_type database table.
 * 
 */
@Entity
@Table(name="movie_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="TYPE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private byte typeId;

	@Column(name="TYPE_NAME")
	private String typeName;

	//bi-directional many-to-one association to Movy
	@OneToMany(mappedBy="movieType" , fetch = FetchType.LAZY)
	private List<Movies> movies;


}