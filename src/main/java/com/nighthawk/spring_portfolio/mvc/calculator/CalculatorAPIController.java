package com.nighthawk.spring_portfolio.mvc.calculator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nighthawk.spring_portfolio.mvc.calculator.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



import java.util.*;

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/calculator")
public class CalculatorAPIController {

    // Autowired enables Control to connect HTML and POJO Object to database easily for CRUD operations
    @Autowired
    private CalculatorJPA repository;

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
    public ResponseEntity<List<CalculatorObject>> getCalculatorObjects() {
        // Get user's objects by userid
        return new ResponseEntity<>( repository.findByowner(getUserName()), HttpStatus.OK);
    }

    @GetMapping("/create/{expressionInput}") 
    public ResponseEntity<List<CalculatorObject>> createCalculatorObject(@PathVariable String expressionInput) {
        // Create new object and save to repo
        String username = getUserName();
        CalculatorObject a = new CalculatorObject(expressionInput, username);
        repository.save(a);
        return new ResponseEntity<>( repository.findByowner(username), HttpStatus.OK);
    }

    @GetMapping("/calculate/{objectID}/{expressionInput}")
    public ResponseEntity<CalculatorObject> calculate(@PathVariable int objectID, @PathVariable String expressionInput) {
        CalculatorObject a = repository.findById(objectID).get();
        //Check if owner matches
        if (!(a.getOwner().equals(getUserName()))) {
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        }
        // Calculate KE and save to repo
        a.calculate(expressionInput);
        repository.save(a);
        return new ResponseEntity<>( a, HttpStatus.OK);
    }
    
    @GetMapping("/delete/{objectID}")
    public ResponseEntity<List<CalculatorObject>> delete(@PathVariable int objectID) {
        CalculatorObject a = repository.findById(objectID).get();
        // Check if owner matches
        if (!(a.getOwner().equals(getUserName()))) {
            return new ResponseEntity<>(repository.findByowner(getUserName()), HttpStatus.BAD_REQUEST);
        }
        // Delete object from repo
        repository.delete(a);
        return new ResponseEntity<>(repository.findByowner(getUserName()), HttpStatus.OK);
    }
}