package com.nighthawk.spring_portfolio.mvc.person;

import com.nighthawk.spring_portfolio.mvc.role.Role;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.*;

import static javax.persistence.FetchType.EAGER;

/*
Person is a POJO, Plain Old Java Object.
First set of annotations add functionality to POJO
--- @Setter @Getter @ToString @NoArgsConstructor @RequiredArgsConstructor
The last annotation connect to database
--- @Entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@TypeDef(name="json", typeClass = JsonType.class)
public class Person {
    
    // automatic unique identifier for Person record
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Size(min=5)
    @Column(unique=true)
    @Email
    private String email;

    @NotEmpty
    private String password;

    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();

    // @NonNull: Places this in @RequiredArgsConstructor
    @NonNull
    @Size(min = 2, max = 30, message = "Name (2 to 30 chars)")
    private String name;
    

    /* HashMap is used to store JSON for daily "stats"
    "stats": {
        "2022-11-13": {
            "calories": 2200,
            "steps": 8000
        }
    }
    */
    // @Type(type="json")
    // @Column(columnDefinition = "jsonb")
    // private Map<String,Map<String, Object>> stats = new HashMap<>(); 

    // ArrayList of stats
    // private ArrayList<Map<String,Map<String, Object>>> statArray = new ArrayList<Map<String,Map<String, Object>>>();
    

    // Constructor used when building object from an API
    public Person(String email, String password, String name, Role role) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.roles.add(role);
    }

    // A custom getter to return age from dob attribute
    // public int getAge() {
    //     if (this.dob != null) {
    //         LocalDate birthDay = this.dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    //         return Period.between(birthDay, LocalDate.now()).getYears(); }
    //     return -1;
    // }

    // main class as a tester
    public static void main(String[] args) {
        // Person empty object
        Person p1 = new Person();

        // using gregorian calendar to initialize tester date object
        Date dob2 = new GregorianCalendar(2005, 3, 4).getTime();
        Person p2 = new Person("johnny@gmail.com", "123johnny", "Johnny Coder", dob2);
        
        
        System.out.println(p1);
        System.out.println(p2);
     }

}