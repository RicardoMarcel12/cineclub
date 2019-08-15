/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.controller;

import com.cineclub.rental.model.AppUser;
import com.cineclub.rental.repository.AppUserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author keiic
 */
@RestController
@RequestMapping("/api")
public class AppUserController {
    
    @Autowired
    AppUserRepository appUserRepository;
    
    @GetMapping("/users")
    public List<AppUser> getAllUsers(){
        return appUserRepository.findAll();
    }
    
}
