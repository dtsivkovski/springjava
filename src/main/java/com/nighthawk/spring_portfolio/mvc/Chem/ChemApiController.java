package com.nighthawk.spring_portfolio.mvc.Chem;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nighthawk.spring_portfolio.mvc.physics.PhysJPA;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.*;

@RestController
@RequestMapping("/api/Chem")
public class ChemApiController {

    @Autowired
    private ChemJpa repository;

    private String getUserName() {
        // gets username from authentication, used to attribute objects to users
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return username;
    }

    @GetMapping("/get/")
    public ResponseEntity<List<ChemObject>> getChemObjects() {
        // Get user's objects by userid
        return new ResponseEntity<>( repository.findByowner(getUserName()), HttpStatus.OK);
    }

    @GetMapping("/create/{mass}") 
    public ResponseEntity<List<ChemObject>> createChemObject(@PathVariable double mass) {
        // Create new object and save to repo
        String username = getUserName();
        ChemObject a = new ChemObject(mass, username);
        repository.save(a);
        return new ResponseEntity<>( repository.findByowner(username), HttpStatus.OK);
    }

    @GetMapping("/calDensity/{")
    {
        
    }
    
    
    
    /* 
    @WebServlet("/density")
    public class DensityServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            String mass = request.getParameter("mass");
            String volume = request.getParameter("volume");
            double density = Double.parseDouble(mass) / (Double.parseDouble(volume) / 1000);
            request.setAttribute("density", density);
            request.getRequestDispatcher("densityResult.jsp").forward(request, response);
        }
    }
    */
}
    
    /*
    GET List of physobjects
     */
    // @GetMapping("/get/all")
    // public ResponseEntity<List<PhysObject>> getPhysObjects() {
    //     // Get all objects 
    //     return new ResponseEntity<>( repository.findAll(), HttpStatus.OK);
    // }