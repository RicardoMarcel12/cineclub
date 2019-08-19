/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

/**
 *
 * @author keiic
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordForgotDTO {
    
    @Email
    @NotEmpty
    private String email;
    
    @URL
    @NotEmpty
    private String url;
    
}
