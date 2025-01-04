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

import com.app.animeApplication.entity.Genre;
import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.payloads.GenreDTO;
import com.app.animeApplication.services.GenreService;
import com.app.animeApplication.services.MovieService;

@RestController
@RequestMapping("/api/public/genre")
public class GenreController {

	@Autowired
    private GenreService genreService;
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
    public ResponseEntity<?> getGenreById(@PathVariable Long id) {
        Genre genre = this.genreService.getGenreById(id);
        if (genre != null) {
            return ResponseEntity.ok(genre);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genre Not Found");
        }
    }

    @PostMapping("/add")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addGenre(@RequestBody GenreDTO genreDTO) {
    	
    	if(this.genreService.addGenre(genreDTO)) {
    		 return ResponseEntity.ok("Genre added");
    	}
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Genre invalid");
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
