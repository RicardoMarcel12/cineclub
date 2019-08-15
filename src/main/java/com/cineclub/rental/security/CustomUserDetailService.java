/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.security;

import com.cineclub.rental.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 *
 * @author keiic
 */
@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;
    
    public CustomUserDetailService(AppUserRepository appUserRepository){
        this.appUserRepository = appUserRepository;
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User: "+username+" not registered"));
    }
    
}
