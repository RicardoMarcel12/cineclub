/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.repository;

import com.cineclub.rental.model.AppPasswordResetToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author keiic
 */
@Repository
public interface AppPasswordResetTokenRepository extends JpaRepository<AppPasswordResetToken, Long> {
    Optional<AppPasswordResetToken> findByToken(String token);
}
