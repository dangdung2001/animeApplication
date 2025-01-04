package com.app.animeApplication.services;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.mapper.EpisodeMapper;
import com.app.animeApplication.mapper.MovieMapper;
import com.app.animeApplication.payloads.EpisodeDTO;
import com.app.animeApplication.payloads.MovieDTO;
import com.app.animeApplication.payloads.SimpleMovieDTO;
import com.app.animeApplication.reposiroty.MovieRepository;

@Service
public class MovieService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
	
	@Autowired
    private MovieRepository movieRepository;
	
	@Autowired
	private MovieMapper movieMapper;
	
	@Autowired
	private EpisodeMapper episodeMapper;
	
	public Page<SimpleMovieDTO> getAllMovies(int pages, int size) {
		Pageable pageable  = PageRequest.of(pages, size);
		Page<Movies> Movies =  movieRepository.findAll(pageable);
		
		Page<SimpleMovieDTO> simpleMovieDTOs = Movies.map(movie -> this.movieMapper.toMovieSimpleDTO(movie));
		
		LOGGER.info("simple movies :" + simpleMovieDTOs.getContent());
		
		return simpleMovieDTOs;
	}
	
	
	
	public List<EpisodeDTO> getEpisodesByMovieID(Long movieId){
		
		Movies movie = this.movieRepository.findMovieWithEpisodes(movieId);
		
		List<EpisodeDTO> episodeDTOs = movie.getEpisodes().stream().map(Episode -> this.episodeMapper.toEpisodeDTO(Episode))
				.collect(Collectors.toList());
		
		return episodeDTOs;
	}

	public MovieDTO getMovieById(Long id) {
		
		Movies movie = this.movieRepository.findById(id).orElse(null);
		
		if(movie != null) {
			
			MovieDTO movieDTO = this.movieMapper.toMovieDetailDTO(movie);
			return movieDTO;
		}
		return null;
	}

	public boolean addMovie(MovieDTO movieDTO) {
		
		Movies movieEntity = this.movieMapper.toMovie(movieDTO);
		
		Movies movieDB =  this.movieRepository.save(movieEntity);
		
		return movieDB != null;
	}

	public Movies updateMovie(Movies updatedMovie) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteMovie(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
