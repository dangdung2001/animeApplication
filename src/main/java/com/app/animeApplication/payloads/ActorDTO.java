package com.app.animeApplication.payloads;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActorDTO {

	private Long actorsId;

	private String actorsName;
	
	private List<SimpleMovieDTO> SimpleMovieDTO;
}
