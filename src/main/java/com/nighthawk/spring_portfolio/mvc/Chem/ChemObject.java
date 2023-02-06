package com.nighthawk.spring_portfolio.mvc.Chem;

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

    private String owner;
}
