/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.repository;

import com.cineclub.rental.model.AppUser;
import com.cineclub.rental.model.MrMovie;
import com.cineclub.rental.model.MrUserMovieLike;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keiic
 */
@Repository
public interface MrUserMovieLikeRepository extends JpaRepository<MrUserMovieLike, Long> {
    
    Integer countByMovieId(MrMovie movieId);
    
    Optional<MrUserMovieLike> findByMovieIdAndUserId(MrMovie movieId, AppUser userId);
}
