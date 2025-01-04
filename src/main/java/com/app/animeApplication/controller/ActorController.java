package com.app.animeApplication.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.animeApplication.entity.Actor;
import com.app.animeApplication.payloads.ActorDTO;
import com.app.animeApplication.services.ActorService;

@RestController
@RequestMapping("/api/public/actor")
public class ActorController {

	@Autowired
    private ActorService actorService;
	
	
//	@GetMapping("/getAll")
//    public ResponseEntity<?> getAllActors(@RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        return ResponseEntity.ok("");
//    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getActorById(@PathVariable Long id) {
    	ActorDTO actorDTO = this.actorService.getActorById(id);
        if (actorDTO != null) {
            return ResponseEntity.ok(actorDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Actor Not Found");
        }
    }

    @PostMapping("/add")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addActor(@RequestBody ActorDTO actorDTO) {
    	if(this.actorService.addActor(actorDTO)) {
    		return ResponseEntity.ok("Actor Added");
    	}
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Faild add Actor");
    }

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
