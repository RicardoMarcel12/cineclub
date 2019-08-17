/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.repository;

import com.cineclub.rental.model.AppUser;
import com.cineclub.rental.model.MrMovie;
import com.cineclub.rental.model.MrRentBuyMovie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keiic
 */
@Repository
public interface MrRentBuyMovieRepository extends JpaRepository<MrRentBuyMovie, Long> {

    public List<MrRentBuyMovie> findByUserIdAndIsActiveTrue(AppUser userId);
    
    public Optional<MrRentBuyMovie> findByUserIdAndMovieIdAndIsActiveTrue(AppUser userId, MrMovie movieId);
    
    public Optional<MrRentBuyMovie> findByUserIdAndMovieIdAndIsActiveTrueAndIsRentBuyFalse(AppUser userId, MrMovie movieId);
    
    public List<MrRentBuyMovie> findByUserIdAndIsRentBuyFalse(AppUser userId);
}
