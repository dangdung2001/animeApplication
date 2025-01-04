package com.app.animeApplication.mapper;

import org.springframework.stereotype.Component;

import com.app.animeApplication.entity.Genre;
import com.app.animeApplication.payloads.GenreDTO;
import com.app.animeApplication.payloads.JwtResponse;

@Component
public class GenreMapper {

	public Genre toGenreEntity(GenreDTO genreDTO) {
		
		Genre genre = new Genre();
		
		genre.setGenreName(genreDTO.getGenreName());
		
		return null;
	}
}
