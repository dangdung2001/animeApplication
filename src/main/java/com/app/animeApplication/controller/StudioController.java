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

import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.entity.Studio;
import com.app.animeApplication.payloads.StudioDTO;
import com.app.animeApplication.services.MovieService;
import com.app.animeApplication.services.StudioService;

@RestController
@RequestMapping("/api/public/studio")
public class StudioController {

	@Autowired
    private StudioService studioService;
//	
//	
//	@GetMapping("/getAll")
//    public ResponseEntity<Page<Movies>> getAllMovies(@RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        Page<Movies> movies = movieService.getAllMovies(page, size);
//        return ResponseEntity.ok(movies);
//    }
//
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getStudioById(@PathVariable Long id) {
        Studio studio = this.studioService.getStudioById(id);
        if (studio != null) {
            return ResponseEntity.ok(studio);
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Studio Not Found");
        
    }

    @PostMapping("/add")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addStudio(@RequestBody StudioDTO studioDTO) {
    	
        if (this.studioService.addStudio(studioDTO)) {
            return ResponseEntity.ok("studio added");
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Studio invalid");
    	
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
