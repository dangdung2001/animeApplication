package com.app.animeApplication.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.animeApplication.entity.Actor;
import com.app.animeApplication.entity.Comment;
import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.mapper.ActorMapper;
import com.app.animeApplication.mapper.CommentMapper;
import com.app.animeApplication.payloads.ActorDTO;
import com.app.animeApplication.payloads.CommentDTO;
import com.app.animeApplication.reposiroty.ActorRepository;
import com.app.animeApplication.reposiroty.CommentRepository;

@Service
public class CommentService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MovieService.class);
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private CommentMapper commentMapper;
	

	public Comment getCommentById(Long id) {
		
		Comment comment = this.commentRepository.findById(id).orElseGet(null);
		
		return comment;
	}

	public boolean addComent(CommentDTO commentDTO) {
		
		Comment comment = this.commentMapper.toCommentEntity(commentDTO);
		
		return this.commentRepository.save(comment) != null;
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
