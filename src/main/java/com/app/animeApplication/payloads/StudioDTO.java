package com.app.animeApplication.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudioDTO {

	private Long studioId;

	private String studioName;
	
	private List<SimpleMovieDTO> SimpleMovieDTO;
}
