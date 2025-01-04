package com.app.animeApplication.payloads;

import lombok.Data;

@Data
public class SimpleMovieDTO {

	private Long movieId;
	
    private String movieTitle;
    
    private String moviePoster; 
    
    private String description;
    
    private int following;
    
    private float avgrating;
}
