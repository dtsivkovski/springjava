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
    private ChemJpa repository;
    private String owner;
    private double mass;
    private double moles;
    private double density;
    private double volume;
    private double molecularWeight;
    // Hashmap with type and result for history of calculations on the object
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "type")
    @Column(name = "result")
    private Map<String, Double> history = new HashMap<String, Double>();


    // Initializes object with mass and username
    ChemObject(double m, double v, double e, String username) {
        mass = m;
        volume=v;
        owner = username;
        System.out.println("initialized");
        calculateD();
    }
        molecularWeight = e;
        moles();

    public void update(double m, double v)
    {
        mass = m;
        volume = v;
        calculateD();
    }

    public void update(double m, double v)
    {
        mass = m;
        volume = v;
        calculateD();
    }
    
    

    private void calculateD() {

        density = mass/volume;
        System.out.println("Density Calculated");

    }
      
    
    public void moles() {
        moles=  mass/molecularWeight;
    }
    }
    


