package com.app.animeApplication.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.animeApplication.entity.Comment;
import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.entity.User;
import com.app.animeApplication.payloads.CommentDTO;
import com.app.animeApplication.payloads.JwtResponse;

@Component
public class CommentMapper {

	@Autowired
	private MovieMapper movieMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	
	public Comment toCommentEntity(CommentDTO commentDTO) {
		
		Movies movie = this.movieMapper.ToMovieEntity(commentDTO.getSimpleMovieDTO());
		
		User user = this.userMapper.toUserEntity(commentDTO.getUser());
		
		Comment comment = new Comment();
		comment.setCommentContent(commentDTO.getCommentContent());
		comment.setCommentId(commentDTO.getID());
		comment.setMovies(movie);
		comment.setUser(user);
		
		return null;
	}
}
