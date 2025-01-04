package com.app.animeApplication.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.animeApplication.entity.Genre;
import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.entity.Studio;
import com.app.animeApplication.mapper.GenreMapper;
import com.app.animeApplication.mapper.StudioMapper;
import com.app.animeApplication.payloads.GenreDTO;
import com.app.animeApplication.payloads.StudioDTO;
import com.app.animeApplication.reposiroty.GenreRepository;
import com.app.animeApplication.reposiroty.StudioRepository;

@Service
public class GenreService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
	
	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private GenreMapper genreMapper;
	

	public Genre getGenreById(Long id) {
		
		Genre genre = this.genreRepository.findById(id).orElseGet(null);
		
		return genre;
	}

	public boolean addGenre(GenreDTO genreDTO) {
		
		Genre genre = this.genreMapper.toGenreEntity(genreDTO);
		
		return this.genreRepository.save(genre) != null;
		
	}

	public Movies updateGenre(Long id, Movies updatedMovie) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteGenreByID(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
