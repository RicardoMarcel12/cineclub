/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.controller;

import com.cineclub.rental.dto.MrUserMovieLikeDTO;
import com.cineclub.rental.exception.ResourceNotFoundException;
import com.cineclub.rental.model.AppUser;
import com.cineclub.rental.model.MrMovie;
import com.cineclub.rental.model.MrUserMovieLike;
import com.cineclub.rental.repository.AppUserRepository;
import com.cineclub.rental.repository.MrMovieRepository;
import com.cineclub.rental.repository.MrUserMovieLikeRepository;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author keiichi
 */
@RestController
@RequestMapping("/m1/likes")
public class MrUserMovieLikeController {
    
    @Autowired
    private MrUserMovieLikeRepository mrUserMovieLikeRepository;
    
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private MrMovieRepository mrMovieRepository;
    
      Logger logger = LoggerFactory.getLogger(MrUserMovieLikeController.class);
    
    @GetMapping("/{id}")
    public ResponseEntity getLikes(@PathVariable("id")Long movieId){
        MrMovie movie = mrMovieRepository.findById(movieId).orElseThrow(()-> new ResourceNotFoundException("Like", "movieId",movieId));
        return ResponseEntity.ok(mrUserMovieLikeRepository.countByMovieId(movie));
    
    }
    
    @PostMapping("")
    public ResponseEntity toggleLike(@RequestBody MrUserMovieLikeDTO form, HttpServletRequest request){
        MrMovie movieId = mrMovieRepository.findById(form.getMovieId()).orElseThrow(()-> new ResourceNotFoundException("Like", "movieId",form.getMovieId()));
        logger.info("entontre la pelicula: "+movieId.getTitle());
        AppUser userId = appUserRepository.findByUsername(form.getUsername()).orElseThrow(()-> new ResourceNotFoundException("Like", "username",form.getUsername()));
        logger.info("entontre el usuario: "+form.getUsername()+" :id>  "+userId.getUserId());
        MrUserMovieLike like;
        try {
            like = mrUserMovieLikeRepository.findByMovieIdAndUserId(movieId, userId).orElseThrow(()-> new ResourceNotFoundException("Like", "existing Like",form.getUsername()+" "+movieId.getTitle()));
            //Toggle Like
            logger.info("ENCONTRAMOS likes para esta movie ni este usuario");
            like.setIsLiked(!like.getIsLiked());
            mrUserMovieLikeRepository.saveAndFlush(like);
            logger.info("GUARDAMOS EL like con id "+like.getLikeId()+" y esta "+ (like.getIsLiked()?"liked":"unliked"));
        } catch (Exception e) {
            logger.info("no hay likes para esta movie ni este usuario");
            //es nuevo
            like = new MrUserMovieLike();
            like.setLikeId(0l);
            like.setMovieId(movieId);
            like.setUserId(userId);
            like.setIsLiked(Boolean.TRUE);

            mrUserMovieLikeRepository.saveAndFlush(like);
           
        }
        return ResponseEntity.noContent().build();
    }
    
    
}
