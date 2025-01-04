package com.app.animeApplication.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.animeApplication.entity.Subtitle;
import com.app.animeApplication.entity.User;

public interface SubtitleRepository extends JpaRepository<Subtitle, Long>{
	
	
}
