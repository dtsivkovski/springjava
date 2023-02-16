package com.nighthawk.spring_portfolio.mvc.Chem;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// JPA is an object-relational mapping (ORM) to persistent data, originally relational databases (SQL). Today JPA implementations has been extended for NoSQL.
public interface ChemJpa extends JpaRepository<ChemObject, Integer> {
    // JPA has many built in methods, these few have been prototyped for this
    // application
    // void save(PhysObject pobj);
    // List<PhysObject> findAllByOrderByuserIDAsc();
    List<ChemObject> findByOwner(String owner);

    /*
     * @Transactional
     * 
     * @Modifying
     * 
     * @Query("update chem_object set mass=?1 where id = ?2 ")
     * int updateChemParams(double mass, int id);
     */
    // List<ChemObject> findById(int id);
}
