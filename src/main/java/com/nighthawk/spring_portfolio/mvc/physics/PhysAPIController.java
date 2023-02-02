package com.nighthawk.spring_portfolio.mvc.physics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



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
    // @GetMapping("/get/all")
    // public ResponseEntity<List<PhysObject>> getPhysObjects() {
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
    public ResponseEntity<List<PhysObject>> getPhysObjects() {
        // Get user's objects by userid
        return new ResponseEntity<>( repository.findByowner(getUserName()), HttpStatus.OK);
    }

    @GetMapping("/create/{mass}") 
    public ResponseEntity<List<PhysObject>> createPhysObject(@PathVariable double mass) {
        // Create new object and save to repo
        String username = getUserName();
        PhysObject a = new PhysObject(mass, username);
        repository.save(a);
        return new ResponseEntity<>( repository.findByowner(username), HttpStatus.OK);
    }

    @GetMapping("/calculateKE/{objectID}/{velocity}")
    public ResponseEntity<PhysObject> calculateKE(@PathVariable int objectID, @PathVariable double velocity) {
        PhysObject a = repository.findById(objectID).get();
        // Check if owner matches
        if (a.getOwner() != getUserName()) {
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        }
        // Calculate KE and save to repo
        a.calculateKE(velocity);
        repository.save(a);
        return new ResponseEntity<>( a, HttpStatus.OK);
    }

    @GetMapping("/calculatePE/{objectID}/{g}/{h}")
    public ResponseEntity<PhysObject> calculatePE(@PathVariable int objectID, @PathVariable double g, @PathVariable double h) {
        PhysObject a = repository.findById(objectID).get();
        // Check if owner matches
        if (a.getOwner() != getUserName()) {
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        }
        // Calculae PE and save to repo
        a.calculatePE(g, h);
        repository.save(a);
        return new ResponseEntity<>( a, HttpStatus.OK);
    }
}