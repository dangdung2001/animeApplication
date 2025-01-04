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

import com.app.animeApplication.entity.Country;
import com.app.animeApplication.entity.Movies;
import com.app.animeApplication.payloads.CountryDTO;
import com.app.animeApplication.services.CountryService;
import com.app.animeApplication.services.MovieService;

@RestController
@RequestMapping("/api/public/country")
public class CountryController {

	@Autowired
    private CountryService countryService;
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
    public ResponseEntity<?> getCoutryById(@PathVariable Long id) {
    	
        CountryDTO countryDTO = this.countryService.getCountryById(id);
        
        if (countryDTO != null) {
            return ResponseEntity.ok(countryDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Country Not Found");
        }
    }
//
    @PostMapping("/add")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCountry(@RequestBody CountryDTO countryDTO) {
    	
    	if(this.countryService.addGenre(countryDTO)) {
    		 return ResponseEntity.ok("Country added");
    	}
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Country invalid");
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
