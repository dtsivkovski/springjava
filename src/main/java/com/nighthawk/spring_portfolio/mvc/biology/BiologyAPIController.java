package com.nighthawk.spring_portfolio.mvc.biology;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nighthawk.spring_portfolio.mvc.biology.punnett.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



import java.util.*;

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/bio")
public class BiologyAPIController {

    // Autowired enables Control to connect HTML and POJO Object to database easily for CRUD operations
    @Autowired
    private PunnettJPA repository;

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

    @GetMapping("/punnett/create/{a1}/{a2}/{b1}/{b2}")
    public ResponseEntity<List<PunnettObject>> createPunnettObject(@PathVariable char a1, @PathVariable char a2, @PathVariable char b1, @PathVariable char b2) {
        // Create new object and save to repo
        String username = getUserName();
        PunnettObject a = new PunnettObject(a1, a2, b1, b2, username);
        repository.save(a);
        return new ResponseEntity<>( repository.findByowner(username), HttpStatus.OK);
    }

    @GetMapping("/punnett/get/all")
    public ResponseEntity<List<PunnettObject>> getPunnettObjects() {
        // Get all objects 
        String username = getUserName();
        return new ResponseEntity<>( repository.findByowner(username), HttpStatus.OK);
    }

    @GetMapping("/punnett/delete/{id}")
    public ResponseEntity<List<PunnettObject>> deletePunnettObject(@PathVariable int id) {
        // Delete object by id
        PunnettObject a = repository.findById(id).get();

        String username = getUserName();
        
        if (a.getOwner().equals(username)) {
            repository.delete(a);
        }
        
        return new ResponseEntity<>( repository.findByowner(username), HttpStatus.OK);
    }

}
