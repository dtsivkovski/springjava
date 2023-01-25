package com.nighthawk.spring_portfolio.mvc.physics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/physics")
public class PhysAPIController {

    // Autowired enables Control to connect HTML and POJO Object to database easily for CRUD operations
    @Autowired
    private PhysJPA repository;

    /*
    GET List of physobjects
     */
    @GetMapping("/get/all")
    public ResponseEntity<List<PhysObject>> getPhysObjects() {
        // Get all objects 
        return new ResponseEntity<>( repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{userID}")
    public ResponseEntity<List<PhysObject>> getPhysObjects(@PathVariable int userID) {
        // Get user's objects by userid
        return new ResponseEntity<>( repository.findByuserID(userID), HttpStatus.OK);
    }

    @GetMapping("/create/{userID}/{mass}") 
    public ResponseEntity<List<PhysObject>> createPhysObject(@PathVariable double mass, @PathVariable int userID) {
        // Create new object and save to repo
        PhysObject a = new PhysObject(mass, userID);
        repository.save(a);
        return new ResponseEntity<>( repository.findByuserID(userID), HttpStatus.OK);
    }

    @GetMapping("/calculateKE/{objectID}/{userID}/{velocity}")
    public ResponseEntity<PhysObject> calculateKE(@PathVariable int objectID,@PathVariable int userID , @PathVariable double velocity) {
        PhysObject a = repository.findById(objectID).get();
        // Check userID matching, this will be replaced by a JWT token
        if (a.getUserID() != userID) {
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        }
        // Calculate KE and save to repo
        a.calculateKE(velocity);
        repository.save(a);
        return new ResponseEntity<>( a, HttpStatus.OK);
    }

    @GetMapping("/calculatePE/{objectID}/{userID}/{g}/{h}")
    public ResponseEntity<PhysObject> calculatePE(@PathVariable int objectID,@PathVariable int userID , @PathVariable double g, @PathVariable double h) {
        PhysObject a = repository.findById(objectID).get();
        // Replace this by JWT token
        if (a.getUserID() != userID) {
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        }
        // Calculae PE and save to repo
        a.calculatePE(g, h);
        repository.save(a);
        return new ResponseEntity<>( a, HttpStatus.OK);
    }
}