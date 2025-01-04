package com.app.animeApplication.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.animeApplication.entity.Actor;
import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.mapper.ActorMapper;
import com.app.animeApplication.payloads.ActorDTO;
import com.app.animeApplication.reposiroty.ActorRepository;

@Service
public class ActorService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
	
	@Autowired
	private ActorRepository actorRepository;
	
	@Autowired
	private ActorMapper actorMapper;
	

	public ActorDTO getActorById(Long id) {
		
		Actor Actor = this.actorRepository.findById(id).orElseGet(null);
		
		ActorDTO actorDTO = this.actorMapper.toActorDTO(Actor);
		
		return actorDTO;
	}

	public boolean addActor(ActorDTO actorDTO) {
		
		Actor actor = this.actorMapper.toActorEntity(actorDTO);
		
		this.actorRepository.save(actor);
		
		return true;
	}

	public Movies updateActor(Long id, Movies updatedMovie) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteActor(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
}
