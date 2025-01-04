package com.app.animeApplication.payloads;

import java.util.List;


import lombok.Data;

@Data
public class MovieTypeDTO {
	
	private byte typeId;

	private String typeName;
	
	private List<SimpleMovieDTO> SimpleMovieDTOs;
}
