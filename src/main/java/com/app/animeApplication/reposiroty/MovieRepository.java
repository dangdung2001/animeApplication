package com.app.animeApplication.reposiroty;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.animeApplication.entity.Movies;

public interface MovieRepository extends JpaRepository<Movies, Long>{
	
	@Query("SELECT m FROM Movies m LEFT JOIN FETCH m.episodes WHERE m.movieid = :movieId")
	Movies findMovieWithEpisodes(@Param("movieId") Long movieId);
}
