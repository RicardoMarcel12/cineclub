/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author keiichi
 */
@Entity
@Getter
@Setter
@Table(name = "APP_USER")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"CreatedAt","UpdatedAt"}, allowGetters = true)
public class AppUser implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView
    private Long id;
    
    @NotBlank
    @JsonView
    private String username;
    
    @NotBlank
    @JsonView
    private String firstName;
    
    @JsonView
    private String secondName;
    
    @NotBlank
    @JsonView
    private String firstSurname;
    
    @JsonView
    private String secondSurname;
    
    @NotBlank
    @JsonView
    private String email;
    
    @Column(nullable=false, updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;
    
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
}
