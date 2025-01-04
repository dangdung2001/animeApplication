package com.app.animeApplication.payloads;

import java.util.Date;
import lombok.Data;

@Data
public class RatingDTO {
	
	private int ratingId;

	private float ratingValue;

	private Date timestamp;
	
	private SimpleMovieDTO SimpleMovieDTO;
	
	private UserDTO userDTO;
}
