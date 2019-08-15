/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.repository;

import com.cineclub.rental.model.MrMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keiichi
 */
@Repository
public interface MrMovieRepository extends JpaRepository<MrMovie, Long> {
    
}
