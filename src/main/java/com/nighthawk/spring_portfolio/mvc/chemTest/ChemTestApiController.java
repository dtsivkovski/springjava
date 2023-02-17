package com.nighthawk.spring_portfolio.mvc.chemTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.*;

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/chemTest")
public class ChemTestApiController {
    
    // Autowired enables Control to connect HTML and POJO Object to database easily for CRUD operations
    @Autowired
    private ChemTestJpa repository;

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
    public ResponseEntity<List<ChemTestObject>> getStatsObjects() {
        // Get user's objects by userid
        return new ResponseEntity<>( repository.findByowner(getUserName()), HttpStatus.OK);
    }

    @GetMapping("/create/{mass}")
    public ResponseEntity<List<ChemTestObject>> createStatsObject(@PathVariable double mass, @PathVariable double volume, @PathVariable double molecularWeight) {
        // Create new object and save to repo
        String username = getUserName();
        ChemTestObject a = new ChemTestObject(mass, username);
        repository.save(a);
        return new ResponseEntity<>( repository.findByowner(username), HttpStatus.OK);
    }

    @GetMapping("/calculateDensity/{objectID}/{volume}")
    public ResponseEntity<ChemTestObject> calculateSDM(@PathVariable int objectID, @PathVariable double volume) {
        ChemTestObject a = repository.findById(objectID).get();
        //Check if owner matches
        if (!(a.getOwner().equals(getUserName()))) {
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        }
        // Calculate the standard deviation of the population mean and save to repo
        a.calculateDensity(volume);
        repository.save(a);
        return new ResponseEntity<>( a, HttpStatus.OK);
    }

    @GetMapping("/calculateMole/{objectID}/{molecularWeight}")
    public ResponseEntity<ChemTestObject> calculateMole(@PathVariable int objectID, @PathVariable double molecularWeight) {
        ChemTestObject a = repository.findById(objectID).get();
        //Check if owner matches
        if (!(a.getOwner().equals(getUserName()))) {
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        }
        // Calculate the standard deviation of the population mean and save to repo
        a.calculateDensity(molecularWeight);
        repository.save(a);
        return new ResponseEntity<>( a, HttpStatus.OK);
    }

    @GetMapping("/scrub/{objectID}")
    public ResponseEntity<ChemTestObject> scrub(@PathVariable int objectID) {
        ChemTestObject a = repository.findById(objectID).get();
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
    public ResponseEntity<List<ChemTestObject>> delete(@PathVariable int objectID) {
        ChemTestObject a = repository.findById(objectID).get();
        // Check if owner matches
        if (!(a.getOwner().equals(getUserName()))) {
            return new ResponseEntity<>(repository.findByowner(getUserName()), HttpStatus.BAD_REQUEST);
        }
        // Delete object from repo
        repository.delete(a);
        return new ResponseEntity<>(repository.findByowner(getUserName()), HttpStatus.OK);
    }
}
