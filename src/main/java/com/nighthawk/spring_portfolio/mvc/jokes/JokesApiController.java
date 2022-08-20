package com.nighthawk.spring_portfolio.mvc.jokes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/jokes")
public class JokesApiController {

    // Autowired enables Control to connect HTML and POJO Object to database easily for CRUD operations
    @Autowired
    private JokesJpaRepository repository;

    /*
    GET List of Jokes
     */
    @GetMapping("/all")
    public ResponseEntity<List<Jokes>> getJokes() {
        return new ResponseEntity<>( repository.findAllByOrderByJokeAsc(), HttpStatus.OK);
    }
}
