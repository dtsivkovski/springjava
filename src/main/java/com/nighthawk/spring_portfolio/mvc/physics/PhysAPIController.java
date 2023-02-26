package com.nighthawk.spring_portfolio.mvc.physics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nighthawk.spring_portfolio.mvc.physics.energy.*;
import com.nighthawk.spring_portfolio.mvc.physics.kinematics.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



import java.util.*;

@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/physics")
public class PhysAPIController {

    // Autowired enables Control to connect HTML and POJO Object to database easily for CRUD operations
    @Autowired
    private PhysJPA repository;
    @Autowired
    private KinematicsJPA repo;

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

    @GetMapping("/energy/get/")
    public ResponseEntity<List<PhysObject>> getPhysObjects() {
        // Get user's objects by userid
        return new ResponseEntity<>( repository.findByowner(getUserName()), HttpStatus.OK);
    }

    @GetMapping("/energy/create/{mass}") 
    public ResponseEntity<List<PhysObject>> createPhysObject(@PathVariable double mass) {
        // Create new object and save to repo
        String username = getUserName();
        PhysObject a = new PhysObject(mass, username);
        repository.save(a);
        return new ResponseEntity<>( repository.findByowner(username), HttpStatus.OK);
    }

    @GetMapping("/energy/calculateKE/{objectID}/{velocity}")
    public ResponseEntity<PhysObject> calculateKE(@PathVariable int objectID, @PathVariable double velocity) {
        PhysObject a = repository.findById(objectID).get();
        //Check if owner matches
        if (!(a.getOwner().equals(getUserName()))) {
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        }
        // Calculate KE and save to repo
        a.calculateKE(velocity);
        repository.save(a);
        return new ResponseEntity<>( a, HttpStatus.OK);
    }

    @GetMapping("/energy/calculatePE/{objectID}/{g}/{h}")
    public ResponseEntity<PhysObject> calculatePE(@PathVariable int objectID, @PathVariable double g, @PathVariable double h) {
        PhysObject a = repository.findById(objectID).get();
        // Check if owner matches
        if (!(a.getOwner().equals(getUserName()))) {
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        }
        // Calculae PE and save to repo
        a.calculatePE(g, h);
        repository.save(a);
        return new ResponseEntity<>( a, HttpStatus.OK);
    }

    @GetMapping("/energy/scrub/{objectID}")
    public ResponseEntity<PhysObject> scrub(@PathVariable int objectID) {
        PhysObject a = repository.findById(objectID).get();
        // Check if owner matches
        if (!(a.getOwner().equals(getUserName()))) {
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        }
        // Scrub object history and save to repo
        a.clearHistory();
        repository.save(a);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @GetMapping("/energy/delete/{objectID}")
    public ResponseEntity<List<PhysObject>> delete(@PathVariable int objectID) {
        PhysObject a = repository.findById(objectID).get();
        // Check if owner matches
        if (!(a.getOwner().equals(getUserName()))) {
            return new ResponseEntity<>(repository.findByowner(getUserName()), HttpStatus.BAD_REQUEST);
        }
        // Delete object from repo
        repository.delete(a);
        return new ResponseEntity<>(repository.findByowner(getUserName()), HttpStatus.OK);
    }

    @GetMapping("/kinematics/create/{name}") 
    public ResponseEntity<List<KinematicsObject>> createKinematicsObject(@PathVariable String name) {
        // Create new object and save to repo
        String username = getUserName();
        KinematicsObject a = new KinematicsObject(name, username);
        repo.save(a);
        return new ResponseEntity<>( repo.findByowner(username), HttpStatus.OK);
    }

    @GetMapping("/kinematics/calculateKinematics/{objectID}/{viInput}/{viKnown}/{vfInput}/{vfKnown}/{aInput}/{aKnown}/{xInput}/{xKnown}/{tInput}/{tKnown}/{unknownInput}")
    public ResponseEntity<KinematicsObject> calculateKinematics(@PathVariable int objectID, @PathVariable double viInput, @PathVariable boolean viKnown, @PathVariable double vfInput, @PathVariable boolean vfKnown, @PathVariable double aInput, @PathVariable boolean aKnown, @PathVariable double xInput, @PathVariable boolean xKnown, @PathVariable double tInput, @PathVariable boolean tKnown, @PathVariable String unknownInput) {
        KinematicsObject a = repo.findById(objectID).get();
        //Check if owner matches
        if (!(a.getOwner().equals(getUserName()))) {
            return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
        }
        // Calculate KE and save to repo
        a.calculateKinematics(viInput, viKnown, vfInput, vfKnown, aInput, aKnown, xInput, xKnown, tInput, xKnown, unknownInput);
        repo.save(a);
        return new ResponseEntity<>(a, HttpStatus.OK);
    }

    @GetMapping("/kinematics/get/")
    public ResponseEntity<List<KinematicsObject>> getKinematicsObjects() {
        // Get all objects 
        String username = getUserName();
        return new ResponseEntity<>( repo.findByowner(username), HttpStatus.OK);
    }

    @GetMapping("/kinematics/delete/{id}")
    public ResponseEntity<List<KinematicsObject>> deleteKinematicsObject(@PathVariable int id) {
        // Delete object by id
        KinematicsObject a = repo.findById(id).get();

        String username = getUserName();
        
        if (a.getOwner().equals(username)) {
            repo.delete(a);
        }
        
        return new ResponseEntity<>( repo.findByowner(username), HttpStatus.OK);
    }
}