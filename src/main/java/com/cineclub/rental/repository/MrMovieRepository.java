/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.repository;

import com.cineclub.rental.model.MrMovie;
import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keiichi
 */
@Repository
public interface MrMovieRepository extends JpaRepository<MrMovie, Long> {
    
    public Page<MrMovie> findByTitleContainingAndIsAvailable(String title,Boolean isAvailable,Pageable pageable);
    
    public Page<MrMovie> findByTitleContaining(String title,Pageable pageable);
    
}
