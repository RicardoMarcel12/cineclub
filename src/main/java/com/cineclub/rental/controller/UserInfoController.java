/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;
/**
 *
 * @author keiic
 */
@RestController()
public class UserInfoController {
    
    @GetMapping("/myInfo")
    public ResponseEntity loggedUserInfo(@AuthenticationPrincipal UserDetails userDetails){
        Map<Object,Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
                .stream()
                .map(a->((GrantedAuthority) a).getAuthority())
                .collect(toList())
        );
        return ok(model);
    }
    
}
