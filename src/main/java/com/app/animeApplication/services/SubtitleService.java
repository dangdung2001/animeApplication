package com.app.animeApplication.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.entity.Studio;
import com.app.animeApplication.entity.Subtitle;
import com.app.animeApplication.mapper.StudioMapper;
import com.app.animeApplication.mapper.SubtitleMapper;
import com.app.animeApplication.payloads.StudioDTO;
import com.app.animeApplication.payloads.SubtitleDTO;
import com.app.animeApplication.reposiroty.StudioRepository;
import com.app.animeApplication.reposiroty.SubtitleRepository;

@Service
public class SubtitleService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
	
	@Autowired
	private SubtitleRepository subtitleRepository;
	
	@Autowired
	private SubtitleMapper subtitleMapper;
	

	public Subtitle getSubtitleById(Long id) {
		
		Subtitle subtitle = this.subtitleRepository.findById(id).orElseGet(null);
		
		return subtitle;
	}

	public boolean addSubtitle(SubtitleDTO subtitleDTO) {
		
		Subtitle subtitle = this.subtitleMapper.toSubtitleEntity(subtitleDTO);
		
		this.subtitleRepository.save(subtitle);
		
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
