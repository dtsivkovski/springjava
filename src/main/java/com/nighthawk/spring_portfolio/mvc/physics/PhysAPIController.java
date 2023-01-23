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
        return new ResponseEntity<>( repository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/get/{userID}")
    public ResponseEntity<List<PhysObject>> getPhysObjects(@PathVariable int userID) {
        return new ResponseEntity<>( repository.findByuserID(userID), HttpStatus.OK);
    }

    @GetMapping("/create/{userID}/{mass}") 
    public ResponseEntity<List<PhysObject>> createPhysObject(@PathVariable double mass, @PathVariable int userID) {
        PhysObject a = new PhysObject(mass, userID);
        repository.save(a);
        return new ResponseEntity<>( repository.findByuserID(userID), HttpStatus.OK);
    }
}