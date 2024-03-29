package com.nighthawk.spring_portfolio.mvc.Chem;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChemObject {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    // private ChemJpa repository;
    @Column(name = "owner")
    private String owner;

    @Column(name = "mass")
    private double mass;

    @Column(name = "volume")
    private double volume;

    @Column(name = "molecular_weight")
    private double molecularWeight;

    @Column(name = "density")
    private double density;

    @Column(name = "mole")
    private double mole;

    // Hashmap with type and result for history of calculations on the object
    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyColumn(name = "type")
    @Column(name = "result")
    private Map<String, Double> history = new HashMap<String, Double>();

    // Initializes object with mass and username
    ChemObject(double m, double v, double moleW, String username) {
        this.mass = m;
        this.volume = v;
        this.molecularWeight = moleW;
        this.owner = username;
        this.density = calculateDensity(volume);
        this.mole = calculateMole(moleW);

    }
    /* 
    public void updateChemParams(doouble mass, double volume, double moleW, )

    
     * // Puts calculations in
     * public void addCalculation(String typeinput, double result) {
     * history.put(typeinput, result);
     * }
     */

    public double calculateDensity(double volume) {
        double density = mass / volume;
        System.out.println("Density Calculated");
        // recentDensity = density;
        // String typeInp = "D (mass = " + mass + ", volume = " + volume + ")";
        // addCalculation(typeInp, density);
        return density;
    }

    public double calculateMole(double molecularWeight) {
        double mole = mass / molecularWeight;
        System.out.println("Mole Calculated");
        // recentMole = mole;
        // String typeInp = "M (mass = " + mass + ", molecular weight = " +
        // molecularWeight + ")";
        // addCalculation(typeInp, mole);
        return mole;
    }

    

    public String toString() {
        return ("{ \"Mass\":" + this.mass +
                ", \"Volume\":" + this.volume +
                ", \"molecular weight\":" + this.molecularWeight +
                ", \"density\":" + this.density +
                ", \"mole\":" + this.mole +
                ", \"User\":" + this.owner +
                " }");
    }

    public static void main(String[] args) {
        ChemObject a = new ChemObject(45, 34, 12, "dan@mail");
        System.out.println(a);
    }

}
