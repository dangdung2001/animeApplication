package com.app.animeApplication.mapper;

import org.springframework.stereotype.Component;

import com.app.animeApplication.entity.Subtitle;
import com.app.animeApplication.payloads.JwtResponse;
import com.app.animeApplication.payloads.SubtitleDTO;

@Component
public class SubtitleMapper {

	

	public Subtitle toSubtitleEntity(SubtitleDTO subtitleDTO) {
		
		Subtitle subtitle = new Subtitle();
		
		subtitle.setSubtitlesName(subtitleDTO.getSubtitlesName());
		
		return subtitle;
	}
}
