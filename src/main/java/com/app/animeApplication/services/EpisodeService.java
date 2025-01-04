package com.app.animeApplication.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.animeApplication.entity.Episode;
import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.entity.Subtitle;
import com.app.animeApplication.mapper.EpisodeMapper;
import com.app.animeApplication.mapper.SubtitleMapper;
import com.app.animeApplication.payloads.EpisodeDTO;
import com.app.animeApplication.payloads.SubtitleDTO;
import com.app.animeApplication.reposiroty.EpisodeRepository;
import com.app.animeApplication.reposiroty.SubtitleRepository;

@Service
public class EpisodeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
	
	@Autowired
	private EpisodeRepository episodeRepository;
	
	@Autowired
	private EpisodeMapper episodeMapper;
	

	public Episode getEpisodeById(Long id) {
		
		Episode episode = this.episodeRepository.findById(id).orElseGet(null);
		
		return episode;
	}

	public boolean addEpisode(EpisodeDTO episodeDTO) {
		
		Episode episode = this.episodeMapper.toEpisodeEntity(episodeDTO);
		
		return this.episodeRepository.save(episode) != null;
	}

	public Movies updateEpisode(Long id, Movies updatedMovie) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteEpisodeByID(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
