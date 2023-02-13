package com.nighthawk.spring_portfolio.mvc.physics;

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
public class PhysObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String owner;
    private double mass;
    private double recentKE;
    private double recentPE;

    // Hashmap with type and result for history of calculations on the object
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "type")
    @Column(name = "result")
    private Map<String, Double> history = new HashMap<String, Double>();

    // Initializes object with mass and username
    PhysObject(double m, String username) {
        mass = m;
        owner = username;
    }

    // Puts calculations in
    public void addCalculation(String typeinput, double result) {
        history.put(typeinput, result);
    }

    // Calculates KE using given vi value, saves it
    public double calculateKE(double vi) {
        double KE = 0.5 * mass * (vi * vi);
        recentKE = KE;
        String typeInp = "KE (v = " + vi + ")";
        history.put(typeInp, KE);
        return KE;
    }

    // Calculates PE using given g and h values, saves it
    public double calculatePE(double g, double h) {
        double PE = mass * g * h;
        recentPE = PE;
        String typeInp = "PE (g = " + g + ", h = " + h + ")";
        addCalculation(typeInp, PE);
        return PE;
    }

    public Map<String, Double> getHistory() {
        return history;
    }

    public double getMass() {
        return mass;
    }

    public void clearHistory() {
        history.clear();
        recentKE = 0;
        recentPE = 0;
    }

    public static void main(String[] args) {
        PhysObject a = new PhysObject(10, "dan@mail");
        a.calculateKE(10);
        a.calculatePE(9.8, 10);
        System.out.println("Mass: " + a.getMass());
        System.out.println("History: " + a.getHistory());

    }
}

