package com.nighthawk.spring_portfolio.mvc.physics.energy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// JPA is an object-relational mapping (ORM) to persistent data, originally relational databases (SQL). Today JPA implementations has been extended for NoSQL.
public interface PhysJPA extends JpaRepository<PhysObject, Integer> {
    // JPA has many built in methods, these few have been prototyped for this application
    // void save(PhysObject pobj);
    // List<PhysObject> findAllByOrderByuserIDAsc();
    List<PhysObject> findByowner(String username);
}
