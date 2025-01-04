package com.app.animeApplication.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.animeApplication.entity.Actor;
import com.app.animeApplication.payloads.ActorDTO;
import com.app.animeApplication.payloads.JwtResponse;
import com.app.animeApplication.payloads.SimpleMovieDTO;

@Component
public class ActorMapper {
	
	@Autowired
	private MovieMapper movieMapper;

	public Actor toActorEntity(ActorDTO ActorDTO) {
		
		Actor actor = new Actor(ActorDTO.getActorsId(), ActorDTO.getActorsName(), null);
		
		return actor;
	}

	public ActorDTO toActorDTO(Actor actor) {
		
		ActorDTO actorDTO = new ActorDTO();
		
		List<SimpleMovieDTO> simpleMovieDTOs =  actor.getMovies().stream().map(movie -> this.movieMapper.toMovieSimpleDTO(movie))
				.collect(Collectors.toList());
		
		
		actorDTO.setActorsId(actor.getActorsId());
		actorDTO.setActorsName(actor.getActorsName());
		actorDTO.setSimpleMovieDTO(simpleMovieDTOs);
		
		return actorDTO;
	}
}
