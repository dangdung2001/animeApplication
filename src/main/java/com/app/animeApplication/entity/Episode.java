package com.app.animeApplication.entity;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * The persistent class for the episode database table.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Episode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="EPISODE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long episodeId;

	@Column(name="DESCRIPTION_EPISODE")
	private String descriptionEpisode;

	@Column(name="DURATION_EPISODE")
	private float durationEpisode;

	@Column(name="EPISODE_NUMBER")
	private int episodeNumber;

	@Temporal(TemporalType.DATE)
	@Column(name="RELEASE_DATE_EPISODE")
	private Date releaseDateEpisode;

	@Column(name="TITLE_EPISODE")
	private String titleEpisode;

	@Column(name="URL_EPISODE")
	private String urlEpisode;

	//bi-directional many-to-one association to Movy
	@ManyToOne
	@JoinColumn(name="MOVIEID")
	private Movies movies;


}



