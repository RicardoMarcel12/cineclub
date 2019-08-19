/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author keiic
 */
@Getter
@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class TokenExpiredException extends RuntimeException{
    
    private String date;
    
    public TokenExpiredException(String date){
        super(String.format("Token Expired, password reset requested on %s", date));
        this.date = date;
    }
}
