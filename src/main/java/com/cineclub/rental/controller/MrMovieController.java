/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.controller;

import com.cineclub.rental.exception.ResourceNotFoundException;
import com.cineclub.rental.model.MrMovie;
import com.cineclub.rental.repository.MrMovieRepository;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author keiichi
 */
@RestController
@RequestMapping("/m1/movies")
public class MrMovieController {

    @Autowired
    private MrMovieRepository mrMovieRepository;

    @GetMapping("")
    public ResponseEntity showAllMovies() {
        return ResponseEntity.ok(mrMovieRepository.findAll());
    }

    @PostMapping("")
    public ResponseEntity saveNewMovie(@Valid @RequestBody MrMovie form, HttpServletRequest request) {
        MrMovie saved = mrMovieRepository.save(form);
        return ResponseEntity.created(
                ServletUriComponentsBuilder
                        .fromContextPath(request)
                        .path("/m1/movies/{id}")
                        .buildAndExpand(saved.getMovieId())
                        .toUri())
                .build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity getMovie(@PathVariable("id") Long movieId){
        return ResponseEntity.ok(mrMovieRepository.findById(movieId).orElseThrow(()-> new ResourceNotFoundException("Movie", "MovieId", movieId)));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity updateMovie(@PathVariable("id")Long movieId,@Valid @RequestBody MrMovie form){
        MrMovie existing = mrMovieRepository.findById(movieId).orElseThrow(()-> new ResourceNotFoundException("Movie", "MovieId", movieId));
        existing.setTitle(form.getTitle());
        existing.setDescription(form.getDescription());
        existing.setImageUrl(form.getImageUrl());
        existing.setSalePrice(form.getSalePrice());
        existing.setRentalPrice(form.getRentalPrice());
        existing.setStock(form.getStock());
        existing.setIsAvailable(form.getIsAvailable());
        
        mrMovieRepository.save(existing);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteMovie(@PathVariable("id")Long movieId){
        MrMovie existing = mrMovieRepository.findById(movieId).orElseThrow(()-> new ResourceNotFoundException("Movie", "MovieId", movieId));
        mrMovieRepository.delete(existing);
        return ResponseEntity.noContent().build();
    }
}
