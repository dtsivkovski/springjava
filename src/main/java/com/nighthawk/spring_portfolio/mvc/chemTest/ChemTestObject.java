package com.nighthawk.spring_portfolio.mvc.chemTest;

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
public class ChemTestObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String owner;
    private double mass;
    private double volume;
    private double molecularWeight;
    private double recentMole;
    private double recentDensity;
    // private double degreesFreedom;
    // private double tailProb;
    // private double zScore;

    // Hashmap with type and result for history of calculations on the object
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "type")
    @Column(name = "result")
    private Map<String, Double> history = new HashMap<String, Double>();

    // Initializes object with degrees of freedom and username
    ChemTestObject(double m, String username) {
        mass = m;
        owner = username;
    }

    // Puts calculations in
    public void addCalculation(String typeinput, double result) {
        history.put(typeinput, result);
    }

    public double calculateDensity(double volume) {
        double density = mass / volume;
        recentDensity = density;
        String typeInp = "Density (m = " + mass + ", v = " + volume + ")";
        addCalculation(typeInp, density);
        return density;
    }

    public double calculateMole(double molecularWeight) {
        double mole = mass / molecularWeight;
        recentMole = mole;
        String typeInp = "Moles (m = " + mass + ", mw = " + molecularWeight + ")";
        addCalculation(typeInp, mole);
        return mole;
    }

    public Map<String, Double> getHistory() {
        return history;
    }

    public double getMass() {
        return mass;
    }

    public void clearHistory() {
        history.clear();
    }

    public static void main(String[] args) {
        ChemTestObject a = new ChemTestObject(15, "dan@mail");
        a.calculateDensity(20);
        System.out.println("Mass: " + a.getMass());
        System.out.println("History: " + a.getHistory());
    }
}

