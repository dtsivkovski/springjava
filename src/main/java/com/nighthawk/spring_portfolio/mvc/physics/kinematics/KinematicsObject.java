package com.nighthawk.spring_portfolio.mvc.physics.kinematics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

@Data  // Annotations to simplify writing code (ie constructors, setters)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class KinematicsObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String owner;

    HashMap<String, Boolean> knownValues = new HashMap<String, Boolean>();
    private double viValue;
    private double vfValue;
    private double aValue;
    private double xValue;
    private double tValue;
    private String unknownValue;
    private double finalAnswer;

    public KinematicsObject(double viInput, boolean viKnown, double vfInput, boolean vfKnown, double aInput, boolean aKnown, double xInput, boolean xKnown, double tInput, boolean tKnown, String unknownInput, String username) {
        if (viKnown == true) {
            knownValues.put("Initial Velocity", viKnown);
            viValue = viInput;
        } else {
            knownValues.put("Initial Velocity", false);
        }
        if (vfKnown == true) {
            knownValues.put("Final Velocity", vfKnown);
            vfValue = vfInput;
        } else {
            knownValues.put("Final Velocity", false);
        }
        if (aKnown == true) {
            knownValues.put("Acceleration", aKnown);
            aValue = aInput;
        } else {
            knownValues.put("Acceleration", false);
        }
        if (xKnown == true) {
            knownValues.put("Displacement", xKnown);
            xValue = xInput;
        } else {
            knownValues.put("Displacement", false);
        }
        if (tKnown == true) {
            knownValues.put("Time", tKnown);
            tValue = tInput;
        } else {
            knownValues.put("Time", false);
        }

        unknownValue = unknownInput;
    
        calculateKinematics();
        
        owner = username;
    }

    private void calculateKinematics() {
        switch(unknownValue) {
            case "Initial Velocity":
                if (knownValues.get("Displacement") == false) {
                    finalAnswer = vfValue - (aValue * tValue);
                } else if (knownValues.get("Final Velocity") == false) {
                    finalAnswer = (xValue - (aValue * Math.pow(tValue, 2))/2)/tValue;
                } else if (knownValues.get("Time") == false) {
                    finalAnswer = Math.sqrt(Math.pow(vfValue, 2) - (2 * aValue * xValue));
                } else if (knownValues.get("Acceleration") == false) {
                    finalAnswer = ((2 * xValue)/tValue) - vfValue;
                }
                break;
            case "Final Velocity":
                if (knownValues.get("Initial Velocity") == false) {
                    finalAnswer = (xValue + (aValue * Math.pow(tValue, 2))/2)/tValue;
                } else if (knownValues.get("Displacement") == false) {
                    finalAnswer = viValue + (aValue * tValue);
                } else if (knownValues.get("Time") == false) {
                    finalAnswer = Math.sqrt(Math.pow(viValue, 2) + (2 * aValue * xValue));
                } else if (knownValues.get("Acceleration") == false) {
                    finalAnswer = ((2 * xValue)/tValue) - viValue;
                }
                break;
            case "Acceleration":
                if (knownValues.get("Initial Velocity") == false) {
                    finalAnswer = (2 * (vfValue * tValue - xValue))/Math.pow(tValue, 2);
                } else if (knownValues.get("Displacement") == false) {
                    finalAnswer = (vfValue - viValue)/tValue;
                } else if (knownValues.get("Time") == false) {
                    finalAnswer = (Math.pow(vfValue, 2) - Math.pow(viValue, 2))/(2 * xValue);
                } else if (knownValues.get("Final Velocity") == false) {
                    finalAnswer = (2 * (xValue - viValue * tValue))/Math.pow(tValue, 2);
                }
                break;
            case "Displacement":
                if (knownValues.get("Initial Velocity") == false) {
                    finalAnswer = vfValue * tValue - (aValue * Math.pow(tValue, 2))/2;
                } else if (knownValues.get("Final Velocity") == false) {
                    finalAnswer = viValue * tValue + (aValue * Math.pow(tValue, 2))/2;
                } else if (knownValues.get("Time") == false) {
                    finalAnswer = (Math.pow(vfValue, 2) - Math.pow(viValue, 2))/(2 * aValue);
                } else if (knownValues.get("Acceleration") == false) {
                    finalAnswer = ((vfValue + viValue) * tValue)/2;
                }
                break;
            case "Time":
                if (knownValues.get("Initial Velocity") == false) {
                    double a = aValue/2;
                    double b = -vfValue;
                    double c = xValue;
                    double alternative1 = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c))/(2 * a);
                    double alternative2 = (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c))/(2 * a);
                    if (alternative2 < 0) {
                        finalAnswer = alternative1;
                    } else {
                        finalAnswer = alternative2;
                    }
                } else if (knownValues.get("Displacement") == false) {
                    finalAnswer = (vfValue - viValue)/aValue;
                } else if (knownValues.get("Final Velocity") == false) {
                    double a = aValue/2;
                    double b = vfValue;
                    double c = -xValue;
                    double alternative1 = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c))/(2 * a);
                    double alternative2 = (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c))/(2 * a);
                    if (alternative2 < 0) {
                        finalAnswer = alternative1;
                    } else {
                        finalAnswer = alternative2;
                    }
                } else if (knownValues.get("Acceleration") == false) {
                    finalAnswer = (xValue * 2)/(vfValue + viValue);
                }
                break;
        }
    }

    public static void main(String[] args) {
        KinematicsObject test = new KinematicsObject(0, true, 0, false, -1.67, true, -1.4, true, 0, false, "Timex", "test");
        System.out.println(test);
        System.out.println(test.getFinalAnswer());
    }
}
