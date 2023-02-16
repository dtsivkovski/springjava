package com.nighthawk.spring_portfolio.mvc.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/stats")
public class StatsApiController {
    
    // Autowired enables Control to connect HTML and POJO Object to database easily for CRUD operations
    @Autowired
    private StatsJpa repository;

    /*
    GET List of Statsobjects
     */
    // @GetMapping("/get/all")
    // public ResponseEntity<List<StatsObject>> getStatsObjects() {
    //     // Get all objects 
    //     return new ResponseEntity<>( repository.findAll(), HttpStatus.OK);
    // }
    private String getUserName() {
        // gets username from authentication, used to attribute objects to users
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return username;
    }

    @GetMapping("/get/")
    public ResponseEntity<List<StatsObject>> getStatsObjects() {
        // Get user's objects by userid
        return new ResponseEntity<>( repository.findByowner(getUserName()), HttpStatus.OK);
    }

    @GetMapping("/create/{sampleSize}") 
    public ResponseEntity<List<StatsObject>> createStatsObject(@PathVariable double sampleSize) {
        // Create new object and save to repo
        String username = getUserName();
        StatsObject a = new StatsObject(sampleSize, username);
        repository.save(a);
        return new ResponseEntity<>( repository.findByowner(username), HttpStatus.OK);
    }

    @GetMapping("/calculateSDM/{objectID}/{standardError}")
    public ResponseEntity<StatsObject> calculateSDM(@PathVariable int objectID, @PathVariable double standardError) {
        StatsObject a = repository.findById(objectID).get();
        //Check if owner matches
        if (!(a.getOwner().equals(getUserName()))) {
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        }
        // Calculate the standard deviation of the population mean and save to repo
        a.calculateSDM(standardError);
        repository.save(a);
        return new ResponseEntity<>( a, HttpStatus.OK);
    }

    @GetMapping("/scrub/{objectID}")
    public ResponseEntity<StatsObject> scrub(@PathVariable int objectID) {
        StatsObject a = repository.findById(objectID).get();
        // Check if owner matches
        if (!(a.getOwner().equals(getUserName()))) {
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        }
        // Scrub object history and save to repo
        a.clearHistory();
        repository.save(a);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @GetMapping("/delete/{objectID}")
    public ResponseEntity<List<StatsObject>> delete(@PathVariable int objectID) {
        StatsObject a = repository.findById(objectID).get();
        // Check if owner matches
        if (!(a.getOwner().equals(getUserName()))) {
            return new ResponseEntity<>(repository.findByowner(getUserName()), HttpStatus.BAD_REQUEST);
        }
        // Delete object from repo
        repository.delete(a);
        return new ResponseEntity<>(repository.findByowner(getUserName()), HttpStatus.OK);
    }
}
