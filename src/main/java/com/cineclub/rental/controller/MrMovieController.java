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
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity showAllMovies(@RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sort", required = false) String sort) {

        //Paging and Sorting
        PageRequest pr = null;

        if (page != null && size > 0 && sort == null) {
            pr = PageRequest.of(page, size, Sort.by("title").ascending());
        } else if (page != null && size > 0 && sort != null) {
            if (sort.startsWith("-")) {
                pr = PageRequest.of(page, size, Sort.by(sort.substring(1)).descending());
            } else {
                pr = PageRequest.of(page, size, Sort.by(sort).ascending());
            }
        } else {
            pr = PageRequest.of(0, 15, Sort.by("title").ascending());
        }
        //Determine parameter set
        return ResponseEntity.ok(mrMovieRepository.findByTitleContainingAndIsAvailable(title == null ? "" : title, true, pr));
    }

    @GetMapping("/admin")
    public ResponseEntity showAllMoviesAdmin(@RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "isAvailable", required = false) Boolean isAvailable,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size,
            @RequestParam(value = "sort", required = false) String sort) {

        //Paging and Sorting
        PageRequest pr = null;

        if (page != null && size > 0 && sort == null) {
            pr = PageRequest.of(page, size, Sort.by("title").ascending());
        } else if (page != null && size > 0 && sort != null) {
            if (sort.startsWith("-")) {
                pr = PageRequest.of(page, size, Sort.by(sort.substring(1)).descending());
            } else {
                pr = PageRequest.of(page, size, Sort.by(sort).ascending());
            }
        } else {
            pr = PageRequest.of(0, 15, Sort.by("title").ascending());
        }
        //Determine parameter set
        if (isAvailable != null) {
            return ResponseEntity.ok(mrMovieRepository.findByTitleContainingAndIsAvailable(title == null ? "" : title, isAvailable, pr));
        } else {
            return ResponseEntity.ok(mrMovieRepository.findByTitleContaining(title == null ? "" : title, pr));
        }
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
    public ResponseEntity getMovie(@PathVariable("id") Long movieId) {
        return ResponseEntity.ok(mrMovieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie", "MovieId", movieId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateMovie(@PathVariable("id") Long movieId, @Valid @RequestBody MrMovie form) {
        MrMovie existing = mrMovieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie", "MovieId", movieId));
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
    public ResponseEntity deleteMovie(@PathVariable("id") Long movieId) {
        MrMovie existing = mrMovieRepository.findById(movieId).orElseThrow(() -> new ResourceNotFoundException("Movie", "MovieId", movieId));
        mrMovieRepository.delete(existing);
        return ResponseEntity.noContent().build();
    }
}
