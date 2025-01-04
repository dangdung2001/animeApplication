package com.app.animeApplication.payloads;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {

	private Long movieid;

	private float avgrating;

	private String description;
	
	private String duration;

	private int following;

	private String image;

	private String name;
	
	private String quality;

	private Date releasedate;

	private int runningtime;

	private String showtimes;

	private String state;

	private String website;
	
	private List<CommentDTO> commentDTOs;
	
	private List<EpisodeDTO> episodeDTOs;
	
	private List<ActorDTO> actorDTOs;
	
	private List<CountryDTO> countrieDTOs;
	
	private List<GenreDTO> genreDTOs;
	
	private List<StudioDTO> studioDTOs;
	
	private List<SubtitleDTO> subtitleDTOs;
	
	private MovieTypeDTO movieType;
}
