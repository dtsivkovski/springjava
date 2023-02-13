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

    private double vi;
    private double vf;
    private double a;
    private double x;
    private double t;

    public KinematicsObject(double viInput, double vfInput, double aInput, double xInput, double tInput, String username) {
        vi = viInput;
        vf = vfInput;
        a = aInput;
        x = xInput;
        t = tInput;

        calculateKinematics();
        
        owner = username;
    }

    private void calculateKinematics() {
        System.out.println("");
    }

    public static void main(String[] args) {
        
    }

}
