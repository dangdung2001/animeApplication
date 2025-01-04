package com.app.animeApplication.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.animeApplication.entity.Episode;
import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.payloads.EpisodeDTO;

@Component
public class EpisodeMapper {

	@Autowired
	private MovieMapper movieMapper;
	

	public Episode toEpisodeEntity(EpisodeDTO episodeDTO) {
		
		Episode episode = new Episode();
		
		Movies movie = this.movieMapper.ToMovieEntity(episodeDTO.getSimpleMovieDTO());
		
		episode.setTitleEpisode(null);
		episode.setDescriptionEpisode(null);
		episode.setDurationEpisode(0);
		episode.setEpisodeNumber(0);
		episode.setReleaseDateEpisode(null);
		episode.setUrlEpisode(null);
		episode.setMovies(movie);
		
		return episode;
	}


	public EpisodeDTO toEpisodeDTO( Episode episode) {
		
		EpisodeDTO episodeDTO = new EpisodeDTO();
		
		episodeDTO.setEpisodeId(episode.getEpisodeId());
		episodeDTO.setTitleEpisode(episode.getTitleEpisode());
		episodeDTO.setDescriptionEpisode(episode.getDescriptionEpisode());
		episodeDTO.setEpisodeNumber(episode.getEpisodeNumber());
		episodeDTO.setDurationEpisode(episode.getDurationEpisode());
		episodeDTO.setReleaseDateEpisode(episode.getReleaseDateEpisode());
		episodeDTO.setUrlEpisode(episode.getUrlEpisode());
		
		return episodeDTO;
	}
}





