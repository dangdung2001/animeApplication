package com.app.animeApplication.payloads;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EpisodeDTO {
	
	private Long episodeId;
	
	private String descriptionEpisode;

	private float durationEpisode;

	private int episodeNumber;

	private Date releaseDateEpisode;

	private String titleEpisode;

	private String urlEpisode;
	
	private SimpleMovieDTO SimpleMovieDTO;
}
