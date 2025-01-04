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
import com.app.animeApplication.payloads.EpisodeDTO;
import com.app.animeApplication.payloads.MovieDTO;
import com.app.animeApplication.payloads.SimpleMovieDTO;
import com.app.animeApplication.services.MovieService;

@RestController
@RequestMapping("/api/public/movie")
public class MovieController {

	@Autowired
    private MovieService movieService;
	
	
	@GetMapping("/getAll")
    public ResponseEntity<Page<SimpleMovieDTO>> getAllMovies(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size) {
        Page<SimpleMovieDTO> movies = movieService.getAllMovies(page, size);
        return ResponseEntity.ok(movies);
    }
	
	@GetMapping("/{movieID}/episodes")
    public ResponseEntity<?> getAllEpisodeByMovieID(@PathVariable("movieID") Long movieID) {
        
		List<EpisodeDTO> episodeDTOs = this.movieService.getEpisodesByMovieID(movieID);
		
		if(episodeDTOs != null && !episodeDTOs.isEmpty()) {
			
			return ResponseEntity.ok(episodeDTOs);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID Movie Or Episodes Not Found");
    }
	

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getMovieById(@PathVariable Long id) {
        MovieDTO movieDTO = movieService.getMovieById(id);
        if (movieDTO != null) {
            return ResponseEntity.ok(movieDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie Not Found");
        }
    }

    @PostMapping("/add")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addMovie(@RequestBody MovieDTO movieDTO) {
       this.movieService.addMovie(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("movie added");
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Movies> updateMovie( @RequestBody Movies updatedMovie) {
        Movies movie = movieService.updateMovie(updatedMovie);
        if (movie != null) {
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        boolean isDeleted = movieService.deleteMovie(id);
        if (isDeleted) {
            return ResponseEntity.ok("Movie deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Movie not found");
        }
    }
}
