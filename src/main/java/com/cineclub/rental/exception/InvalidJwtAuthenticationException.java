/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.exception;

import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author keiic
 */
public class InvalidJwtAuthenticationException extends AuthenticationException {
    
    
    public InvalidJwtAuthenticationException(String msg) {
        super(msg);
    }
    
}
