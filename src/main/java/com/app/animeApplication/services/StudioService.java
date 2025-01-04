package com.app.animeApplication.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.animeApplication.entity.Actor;
import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.entity.Studio;
import com.app.animeApplication.mapper.ActorMapper;
import com.app.animeApplication.mapper.StudioMapper;
import com.app.animeApplication.payloads.ActorDTO;
import com.app.animeApplication.payloads.StudioDTO;
import com.app.animeApplication.reposiroty.ActorRepository;
import com.app.animeApplication.reposiroty.StudioRepository;

@Service
public class StudioService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
	
	@Autowired
	private StudioRepository studioRepository;
	
	@Autowired
	private StudioMapper studioMapper;
	

	public Studio getStudioById(Long id) {
		
		Studio studio = this.studioRepository.findById(id).orElseGet(null);
		
		return studio;
	}

	public boolean addStudio(StudioDTO StudioDTO) {
		
		Studio studio = this.studioMapper.toStudioEntity(StudioDTO);
		
		this.studioRepository.save(studio);
		
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
