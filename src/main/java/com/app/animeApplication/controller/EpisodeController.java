package com.app.animeApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.animeApplication.entity.Episode;
import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.payloads.EpisodeDTO;
import com.app.animeApplication.services.EpisodeService;
import com.app.animeApplication.services.MovieService;

@RestController
@RequestMapping("/api/public/episode")
public class EpisodeController {

	@Autowired
    private EpisodeService episodeService;
	
	
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getEpisodeById(@PathVariable Long id) {
        Episode episode = this.episodeService.getEpisodeById(id);
        if (episode != null) {
            return ResponseEntity.ok(episode);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Episode Not Found");
        }
    }
//
    @PostMapping("/add")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addEpisode(@RequestBody EpisodeDTO episodeDTO) {
    	
    	if(this.episodeService.addEpisode(episodeDTO)) {
    		 return ResponseEntity.ok("Episode Added");
        } 
    	
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Episode Invalid");
    	
    }
//
//    @PutMapping("/update/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Movies> updateMovie(@PathVariable Long id, @RequestBody Movies updatedMovie) {
//        Movies movie = movieService.updateMovie(id, updatedMovie);
//        if (movie != null) {
//            return ResponseEntity.ok(movie);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
//
//    @DeleteMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
//        boolean isDeleted = movieService.deleteMovie(id);
//        if (isDeleted) {
//            return ResponseEntity.ok("Movie deleted successfully");
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
//        }
//    }
}
