/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.utils;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author keiic
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppMail {
    private String from;
    private String to;
    private String subject;
    private Map<String,Object> model;
    
    
    
    
}
