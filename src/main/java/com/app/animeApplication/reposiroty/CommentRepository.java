package com.app.animeApplication.reposiroty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.animeApplication.entity.Comment;
import com.app.animeApplication.entity.User;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	
	
}
