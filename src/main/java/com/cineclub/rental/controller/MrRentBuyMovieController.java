/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.controller;

import com.cineclub.rental.dto.MrRentBuyMovieDTO;
import com.cineclub.rental.exception.ResourceNotFoundException;
import com.cineclub.rental.model.AppUser;
import com.cineclub.rental.model.MrMovie;
import com.cineclub.rental.model.MrRentBuyMovie;
import com.cineclub.rental.repository.AppUserRepository;
import com.cineclub.rental.repository.MrMovieRepository;
import com.cineclub.rental.repository.MrRentBuyMovieRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author keiic
 */
@RestController
public class MrRentBuyMovieController {

    @Autowired
    private MrRentBuyMovieRepository mrRentBuyMovieRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private MrMovieRepository mrMovieRepository;

    /*RENT MOVIES*/
    @PostMapping("/r1/rent")
    public ResponseEntity rentMovie(
            @Valid @RequestBody MrRentBuyMovieDTO form) {

        MrMovie existing = mrMovieRepository.findById(form.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Rent Movie", "MovieId", form.getMovieId()));
        AppUser userId = appUserRepository.findByUsername(form.getUsername()).orElseThrow(() -> new ResourceNotFoundException("Rent Movie", "username", form.getUsername()));
        Integer copiesQty = form.getCopiesQty() != null && form.getCopiesQty() > 0 ? form.getCopiesQty() : 1;
        Integer days = form.getDays() != null && form.getDays() > 0 ? form.getDays() : 1;
        BigDecimal operationAmount = existing.getRentalPrice().multiply(new BigDecimal(copiesQty * days));
        Calendar calculate = Calendar.getInstance();
        calculate.add(Calendar.DAY_OF_YEAR, days);
        Date returnDate = calculate.getTime();
        /*creating movie buy object*/
        MrRentBuyMovie rent = new MrRentBuyMovie();
        rent.setMovieId(existing);
        rent.setUserId(userId);
        rent.setCopiesQty(copiesQty);
        rent.setDays(days);
        rent.setReturnDate(returnDate);
        rent.setOperationAmount(operationAmount);
        rent.setIsActive(Boolean.TRUE);
        rent.setIsRentBuy(Boolean.TRUE);/*RENT IS TRUE*/

        mrRentBuyMovieRepository.save(rent);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/r1/rent/{username}")
    public ResponseEntity getActiveRentsByUsername(@PathVariable("username") String username) {
        AppUser userId = appUserRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Rent Movie", "username", username));
        return ResponseEntity.ok(mrRentBuyMovieRepository.findByUserIdAndIsActiveTrue(userId));
    }

    @PutMapping("/r2/rent")
    public ResponseEntity returnRentedMovieBalance(@Valid @RequestBody MrRentBuyMovieDTO form) {
        MrMovie movieId = mrMovieRepository.findById(form.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Rent Movie", "MovieId", form.getMovieId()));
        AppUser userId = appUserRepository.findByUsername(form.getUsername()).orElseThrow(() -> new ResourceNotFoundException("Rent Movie", "username", form.getUsername()));
        MrRentBuyMovie rent = mrRentBuyMovieRepository.findByUserIdAndMovieIdAndIsActiveTrue(userId, movieId).orElseThrow(() -> new ResourceNotFoundException("Rent Movie", "Rental Record", form.getUsername()));
        LocalDate now = LocalDate.now();
        LocalDate returnDate = rent.getReturnDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period overtime = Period.between(returnDate, now);
        Integer overtimeDays = overtime.getDays();

        if (overtimeDays > 0) {
            BigDecimal originalAmount = rent.getOperationAmount();
            BigDecimal overtimePenalty = movieId.getRentalPrice().multiply(new BigDecimal(overtimeDays));
            rent.setOperationAmount(originalAmount.add(overtimePenalty));
            rent.setDays(rent.getDays() + overtimeDays);
        }

        rent = mrRentBuyMovieRepository.save(rent);
        Map<Object, Object> response = new HashMap<>();
        response.put("overtimeDays", overtimeDays);
        response.put("amountToPay", rent.getOperationAmount());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/r3/rent")
    public ResponseEntity returnRentedMovieConfirm(@Valid @RequestBody MrRentBuyMovieDTO form) {
        MrMovie movieId = mrMovieRepository.findById(form.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Rent Movie", "MovieId", form.getMovieId()));
        AppUser userId = appUserRepository.findByUsername(form.getUsername()).orElseThrow(() -> new ResourceNotFoundException("Rent Movie", "username", form.getUsername()));
        MrRentBuyMovie rent = mrRentBuyMovieRepository.findByUserIdAndMovieIdAndIsActiveTrue(userId, movieId).orElseThrow(() -> new ResourceNotFoundException("Rent Movie", "Rental Record", form.getUsername()));

        rent.setIsActive(Boolean.FALSE);
        rent.setReturnedOn(new Date());
        mrRentBuyMovieRepository.save(rent);
        return ResponseEntity.noContent().build();
    }

    /*BUY MOVIES*/
    @PostMapping("/b1/buy")
    public ResponseEntity buyMovieRequest(
            @Valid @RequestBody MrRentBuyMovieDTO form) {

        MrMovie existing = mrMovieRepository.findById(form.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Buy Movie", "MovieId", form.getMovieId()));
        AppUser userId = appUserRepository.findByUsername(form.getUsername()).orElseThrow(() -> new ResourceNotFoundException("Buy Movie", "username", form.getUsername()));
        Integer copiesQty = form.getCopiesQty() != null && form.getCopiesQty() > 0 ? form.getCopiesQty() : 1;
        BigDecimal operationAmount = existing.getSalePrice().multiply(new BigDecimal(copiesQty));
        /*creating movie buy object*/
        Map<Object, Object> response = new HashMap<>();
        if (existing.getStock() > form.getCopiesQty()) {
            MrRentBuyMovie buy = new MrRentBuyMovie();
            buy.setMovieId(existing);
            buy.setUserId(userId);
            buy.setCopiesQty(copiesQty);
            buy.setDays(0);
            buy.setOperationAmount(operationAmount);
            buy.setIsActive(Boolean.TRUE);
            buy.setIsRentBuy(Boolean.FALSE);/*BUY IS TRUE*/

            mrRentBuyMovieRepository.save(buy);
            response.put("movieId", existing.getMovieId());
            response.put("copiesQty", buy.getCopiesQty());
            response.put("amountToPay", buy.getOperationAmount());
        } else {
            response.put("errorMsg", "Not enough movies on stock");

        }

        return ResponseEntity.ok(response);
    }

    @PutMapping("/b2/buy")
    public ResponseEntity buyMovieConfirm(@Valid @RequestBody MrRentBuyMovieDTO form) {
        MrMovie movieId = mrMovieRepository.findById(form.getMovieId()).orElseThrow(() -> new ResourceNotFoundException("Buy Movie", "MovieId", form.getMovieId()));
        AppUser userId = appUserRepository.findByUsername(form.getUsername()).orElseThrow(() -> new ResourceNotFoundException("Buy Movie", "username", form.getUsername()));
        MrRentBuyMovie rent = mrRentBuyMovieRepository.findByUserIdAndMovieIdAndIsActiveTrueAndIsRentBuyFalse(userId, movieId).orElseThrow(() -> new ResourceNotFoundException("Buy Movie", "Buy Record", form.getUsername()));

        rent.setIsActive(Boolean.FALSE);

        mrRentBuyMovieRepository.save(rent);
        return ResponseEntity.noContent().build();
    }

    /*purchase history*/
    @GetMapping("/b1/buy/{username}")
    public ResponseEntity getPurchasesByUsername(@PathVariable("username") String username) {
        AppUser userId = appUserRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Buy Movie", "username", username));
        return ResponseEntity.ok(mrRentBuyMovieRepository.findByUserIdAndIsRentBuyFalse(userId));
    }
}
