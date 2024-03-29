package com.nighthawk.spring_portfolio.mvc.stats;

import java.util.HashMap;
import java.util.Map;
import java.lang.Math;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StatsObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String owner;
    private double sampleSize;
    private double standardError;
    private double recentSDM;
    // private double newSampleSize;
    // private double degreesFreedom;
    // private double tailProb;
    // private double zScore;

    // Hashmap with type and result for history of calculations on the object
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "type")
    @Column(name = "result")
    private Map<String, Double> history = new HashMap<String, Double>();

    // Initializes object with sample size and username
    StatsObject(double n, String username) {
        sampleSize = n;
        owner = username;
    }

    // Puts calculations in
    public void addCalculation(String typeinput, double result) {
        history.put(typeinput, result);
    }

    // calculates the standard deviation of the sample distribution of means
    public double calculateSDM(double standardError) {
        double sdm = standardError / Math.sqrt(sampleSize);
        recentSDM = sdm;
        String typeInp = "SDM (n = " + sampleSize + ", SE = " + standardError + ")";
        addCalculation(typeInp, sdm);
        return sdm;
    }

    // public double setSampleSize(double newSampleSize) {
    //     String typeInp = "Update Sample Size (n = " + sampleSize + " to " + newSampleSize + ")";
    //     sampleSize = newSampleSize;
    //     addCalculation(typeInp, newSampleSize);
    //     return newSampleSize;
    // }

    public Map<String, Double> getHistory() {
        return history;
    }

    public double getN() {
        return sampleSize;
    }

    // public double getNewN() {
    //     return newSampleSize;
    // }

    public void clearHistory() {
        history.clear();
    }

    // Tester method for functions above
    public static void main(String[] args) {
        StatsObject a = new StatsObject(15, "dan@mail");
        a.calculateSDM(20);
        System.out.println("Sample size: " + a.getN());
        System.out.println("History: " + a.getHistory());
        // a.setSampleSize(20);
        // System.out.println("Updated sample size: " + a.getNewN());
    }
}

