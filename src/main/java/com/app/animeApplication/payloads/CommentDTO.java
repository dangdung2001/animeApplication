package com.app.animeApplication.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDTO {

	private Long ID;
	
	private String commentContent;
	
	private SimpleMovieDTO SimpleMovieDTO;
	
	private UserDTO user;
}
