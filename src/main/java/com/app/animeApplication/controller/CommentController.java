package com.app.animeApplication.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.animeApplication.entity.Comment;
import com.app.animeApplication.payloads.CommentDTO;
import com.app.animeApplication.services.CommentService;

@RestController
@RequestMapping("/api/public/comment")
public class CommentController {

	@Autowired
    private CommentService commentService;
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
    public ResponseEntity<?> getCommentById(@PathVariable Long id) {
    	Comment comment = this.commentService.getCommentById(id);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment Not Found");
        }
    }
//
    @PostMapping("/add")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addComment(@RequestBody CommentDTO commentDTO) {
    	
    	if(this.commentService.addComent(commentDTO)) {
    		return ResponseEntity.ok("Comment Added");
    	}
    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Comment Invalid");
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
