/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental;

import com.cineclub.rental.model.AppUser;
import com.cineclub.rental.repository.AppUserRepository;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author keiic
 */
@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public void run(String... args) throws Exception {
       /* this.appUserRepository.save(
                AppUser.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("12345"))
                        .firstName("general")
                        .firstSurname("administrator")
                        .email("admin@cineclub.com")
                        .roles(Arrays.asList("ROLE_ADMIN","ROLE_USER"))
                        .build()
        );
        this.appUserRepository.save(
                AppUser.builder()
                        .username("demo")
                        .password(passwordEncoder.encode("12345"))
                        .firstName("John")
                        .firstSurname("Doe")
                        .email("johnDoe@yahoo.com")
                        .roles(Arrays.asList("ROLE_USER"))
                        .build()
        );
        */
        log.debug("printing all users...");
        appUserRepository.findAll().forEach(v -> log.debug(" User :" + v.toString()));
    }
    
}
