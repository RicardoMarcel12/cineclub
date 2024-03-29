/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.repository;

import com.cineclub.rental.model.AppUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keiichi
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{
    
    Optional<AppUser> findByUsername(@Param("username")String username);
    
    Optional<AppUser> findByEmail(@Param("email")String email);
    
    
}
