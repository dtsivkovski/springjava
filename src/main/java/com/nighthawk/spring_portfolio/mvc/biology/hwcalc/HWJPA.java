package com.nighthawk.spring_portfolio.mvc.biology.hwcalc;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;




// JPA is an object-relational mapping (ORM) to persistent data, originally relational databases (SQL). Today JPA implementations has been extended for NoSQL.
public interface HWJPA extends JpaRepository<HWObject, Integer> {
    // JPA has many built in methods, these few have been prototyped for this application
    // void save(PhysObject pobj);
    // List<PhysObject> findAllByOrderByuserIDAsc();
    List<HWObject> findByowner(String username);
}
