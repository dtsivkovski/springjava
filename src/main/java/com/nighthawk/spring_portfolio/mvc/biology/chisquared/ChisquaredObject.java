package com.nighthawk.spring_portfolio.mvc.biology.chisquared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import org.hibernate.hql.internal.ast.tree.ExpectedTypeAwareNode;

import java.util.ArrayList;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ChisquaredObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String owner;

    private double chisquaredValue = 0;

    private int[] observedValues;
    private int[] expectedValues;

    private ArrayList<Double> componentsTable = new ArrayList<Double>();

    public ChisquaredObject(int[] observed, int[] expected, String username) {
        observedValues = new int[observed.length];
        for (int i = 0; i < observedValues.length; i++) {
            observedValues[i] = observed[i];
        }
        expectedValues = new int[expected.length];
        for (int j = 0; j < expectedValues.length; j++) {
            expectedValues[j] = expected[j];
        }

        calculateChisquared();
        
        owner = username;
    }

    private void calculateChisquared() {
        for (int x = 0; x < observedValues.length; x++) {
            double component = Math.pow((double) observedValues[x] - (double) expectedValues[x], 2)/(double) expectedValues[x];
            componentsTable.add(component);
            chisquaredValue += component;
        }
    }

    public static void main(String[] args) {
        int[] observed = {1, 2, 5};
        int[] expected = {3, 3, 2};
        ChisquaredObject test = new ChisquaredObject(observed, expected, "test");
        System.out.println(test);
        System.out.println(test.getChisquaredValue());
    }

}
