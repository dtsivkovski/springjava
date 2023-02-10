package com.nighthawk.spring_portfolio.mvc.stats;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// JPA is an object-relational mapping (ORM) to persistent data, originally relational databases (SQL). Today JPA implementations has been extended for NoSQL.
public interface StatsJpa extends JpaRepository<StatsObject, Integer> {
    // JPA has many built in methods, these few have been prototyped for this application
    // void save(StatsObject pobj);
    // List<PhysObject> findAllByOrderByuserIDAsc();
    List<StatsObject> findByowner(String username);
}
