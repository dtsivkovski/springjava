package com.nighthawk.spring_portfolio.mvc.Chem;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChemObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
<<<<<<< HEAD
=======
    private ChemJpa repository;
>>>>>>> 6185c0800ff6723676a1a43e7cde175bdaa02490
    private String owner;
    private double mass;
    private double moles;
    private double density;
    private double volume;
    private double molecularWeight;
    private double recentDensity;
    private double recentMole;
    // Hashmap with type and result for history of calculations on the object
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "type")
    @Column(name = "result")
    private Map<String, Double> history = new HashMap<String, Double>();


    // Initializes object with mass and username
    ChemObject(double m, String username) {
        mass = m;
        owner = username;
<<<<<<< HEAD
    }

    // Puts calculations in
    public void addCalculation(String typeinput, double result) {
        history.put(typeinput, result);
    }
=======
        System.out.println("initialized");
        calculateD();
        molecularWeight = e;
        moles();
    }

    public void update(double m, double v)
    {
        mass = m;
        volume = v;
        calculateD();
    }
    

    private void calculateD() {
>>>>>>> 6185c0800ff6723676a1a43e7cde175bdaa02490

    public double calculateDensity(double volume) {
        double density = mass/volume;
        System.out.println("Density Calculated");
        recentDensity = density;
        String typeInp = "D (mass = " + mass + ", volume = " + volume + ")";
        addCalculation(typeInp, density);
        return density;
    }
      
<<<<<<< HEAD
    public double calculateMole(double molecularWeight) {
        double mole = mass/molecularWeight;
        System.out.println("Mole Calculated");
        recentMole = mole;
        String typeInp = "M (mass = " + mass + ", molecular weight = " + molecularWeight + ")";
        addCalculation(typeInp, mole);
        return mole;
=======
    
    public void moles() {
        moles =  mass/molecularWeight;
    }
>>>>>>> 6185c0800ff6723676a1a43e7cde175bdaa02490
    }
}
    


