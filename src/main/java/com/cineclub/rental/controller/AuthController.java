/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.controller;

import com.cineclub.rental.dto.AuthenticationRequest;
import com.cineclub.rental.repository.AppUserRepository;
import com.cineclub.rental.security.JwtTokenProvider;
import java.util.HashMap;
import java.util.Map;
import javax.swing.text.html.HTML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author keiichi
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    AppUserRepository userRepository;
    
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest form){
        try {
            String username = form.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, form.getPassword()));
            String token = jwtTokenProvider.createToken(username, userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User: "+username+"not found")).getRoles());
            
            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            return ResponseEntity.ok(model);
            
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }
    
    
}
