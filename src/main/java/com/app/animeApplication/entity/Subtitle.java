package com.app.animeApplication.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * The persistent class for the subtitles database table.
 * 
 */
@Entity
@Table(name="subtitles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subtitle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="SUBTITLES_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long subtitlesId;

	@Column(name="SUBTITLES_NAME")
	private String subtitlesName;

	//bi-directional many-to-many association to Movy
	@ManyToMany( mappedBy = "subtitles",fetch = FetchType.LAZY,cascade = { CascadeType.MERGE })
	private List<Movies> movies;


	
}