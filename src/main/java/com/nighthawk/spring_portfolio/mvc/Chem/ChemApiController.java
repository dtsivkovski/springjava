package com.nighthawk.spring_portfolio.mvc.Chem;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        return new ResponseEntity<>(repository.findByOwner(getUserName()), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<List<ChemObject>> createChemObject(@RequestParam("mass") double mass,
            @RequestParam("volume") double volume,
            @RequestParam("molecularWeight") double molecularWeight) {
        // Create new object and save to repo
        String username = getUserName();
        ChemObject a = new ChemObject(mass, volume, molecularWeight, username);
        repository.save(a);
        System.out.println("saved");
        return new ResponseEntity<>(repository.findByOwner(username), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{Objectid}")
    public ResponseEntity<List<ChemObject>> deleteChem(@PathVariable int Objectid) {
        Optional<ChemObject> optional = repository.findById(Objectid);

        if (optional.isPresent()) {
            ChemObject b = optional.get();
            if (!(b.getOwner().equals(getUserName()))) {
                return new ResponseEntity<>(repository.findByOwner(getUserName()), HttpStatus.BAD_REQUEST);
            }
            repository.delete(b);
        }

        return new ResponseEntity<>(repository.findByOwner(getUserName()), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<List<ChemObject>> updateChem(@RequestParam("id") int id,
            @RequestParam("mass") double m,
            @RequestParam("volume") double v,
            @RequestParam("molecularWeight") double mw) {

        //int updateChemParam = repository.updateChemParams(m, id);
        //System.out.println("Updated:" + updateChemParam);
        /*
         * Optional<ChemObject> optional = repository.findById(id);
         * 
         * if (optional.isPresent()) {
         * ChemObject c = optional.get();
         * if (!(c.getOwner().equals(getUserName()))) {
         * return new ResponseEntity<>(repository.findByOwner(getUserName()),
         * HttpStatus.BAD_REQUEST);
         * }
         * 
         * }
         */
        return new ResponseEntity<>(repository.findByOwner(getUserName()), HttpStatus.OK);
    }

    /*
     * @GetMapping("/calculateDensity/{objectID}/{volume}")
     * public ResponseEntity<ChemObject> calculateD(@PathVariable int
     * objectID, @PathVariable double volume) {
     * ChemObject a = repository.findById(objectID).get();
     * // Check if owner matches
     * if (!(a.getOwner().equals(getUserName()))) {
     * return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
     * }
     * // Calculate the standard deviation of the population mean and save to repo
     * a.calculateDensity(volume);
     * repository.save(a);
     * return new ResponseEntity<>(a, HttpStatus.OK);
     * }
     */

    /*
     * @WebServlet("/density")
     * public class DensityServlet extends HttpServlet {
     * 
     * @Override
     * protected void doPost(HttpServletRequest request, HttpServletResponse
     * response)
     * throws ServletException, IOException {
     * String mass = request.getParameter("mass");
     * String volume = request.getParameter("volume");
     * double density = Double.parseDouble(mass) / (Double.parseDouble(volume) /
     * 1000);
     * request.setAttribute("density", density);
     * request.getRequestDispatcher("densityResult.jsp").forward(request, response);
     * }
     * }
     */
}

/*
 * GET List of physobjects
 */
// @GetMapping("/get/all")
// public ResponseEntity<List<PhysObject>> getPhysObjects() {
// // Get all objects
// return new ResponseEntity<>( repository.findAll(), HttpStatus.OK);
// }