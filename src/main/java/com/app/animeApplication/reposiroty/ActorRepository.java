package com.app.animeApplication.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.animeApplication.entity.Actor;
import com.app.animeApplication.entity.User;

public interface ActorRepository extends JpaRepository<Actor, Long>{
	
	
}
