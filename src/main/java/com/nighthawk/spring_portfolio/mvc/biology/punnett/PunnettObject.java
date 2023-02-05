package com.nighthawk.spring_portfolio.mvc.biology.punnett;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PunnettObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String owner;

    // Allele probabilities
    private double probDominant;
    private double probRecessive;

    private char[] alleles = new char[4];

    private ArrayList<String> genoTable = new ArrayList<String>();

    public PunnettObject(char a1, char a2, char b1, char b2, String username) {
        alleles[0] = a1;
        alleles[1] = a2;
        alleles[2] = b1;
        alleles[3] = b2;

        calculateTable();
        
        owner = username;
    }

    private void calculateTable() {

        // Create table genotypes by cross
        String t1 = "" + alleles[0] + alleles[2];
        String t2 = "" + alleles[1] + alleles[2];
        String t3 = "" + alleles[0] + alleles[3];
        String t4 = "" + alleles[1] + alleles[3];

        // prepare temp table count for 
        String[] tempTable = new String[] {t1, t2, t3, t4};
        probDominant = 0;

        // loop through table
        for (String i : tempTable) {
            // assume all recessive for dominance check
            boolean isDom = false;

            for (int j = 0; j < i.length(); j++) {
                // check if uppercase to determine if dominant
                if (Character.isUpperCase(i.charAt(j))) {
                    isDom = true;
                }
            }
            // if any are uppercase, then add dominant probability
            if (isDom) {
                probDominant += 0.25;
            }

            genoTable.add(i);
            
        }
        
        // calculate recessive
        probRecessive = 1 - probDominant;
        

    }

    public static void main(String[] args) {
        PunnettObject test = new PunnettObject('A', 'a', 'B', 'b', "test");
        System.out.println(test);
        System.out.println(test.getProbDominant());
        System.out.println(test.getProbRecessive());
    }

}
