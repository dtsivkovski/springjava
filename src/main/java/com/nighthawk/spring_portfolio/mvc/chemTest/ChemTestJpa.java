package com.nighthawk.spring_portfolio.mvc.chemTest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// JPA is an object-relational mapping (ORM) to persistent data, originally relational databases (SQL). Today JPA implementations has been extended for NoSQL.
public interface ChemTestJpa extends JpaRepository<ChemTestObject, Integer> {
    // JPA has many built in methods, these few have been prototyped for this application
    // void save(StatsObject pobj);
    // List<PhysObject> findAllByOrderByuserIDAsc();
    List<ChemTestObject> findByowner(String username);
}
