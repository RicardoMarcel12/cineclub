/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.dto;

import javax.validation.constraints.NotEmpty;
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
@NoArgsConstructor
@AllArgsConstructor
public class AppUserRolesDTO {

    @NotEmpty
    private String role;

}
