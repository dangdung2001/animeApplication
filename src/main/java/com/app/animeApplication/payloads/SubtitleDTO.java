package com.app.animeApplication.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubtitleDTO {

	private Long subtitlesId;

	private String subtitlesName;
	
	private List<SimpleMovieDTO> SimplemMovieDTOs;
}
