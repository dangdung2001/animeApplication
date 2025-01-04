package com.app.animeApplication.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenreDTO {

	private Long genreId;

	private String genreName;
	
	private List<SimpleMovieDTO> movies;
}
