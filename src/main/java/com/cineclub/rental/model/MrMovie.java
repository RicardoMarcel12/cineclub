/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 * @author keiic
 */
@Entity
@Getter
@Setter
@Table(name = "MR_MOVIE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt","updatedAt"}, allowGetters = true)    
public class MrMovie implements Serializable {
    
    @Id
    @Column(name = "MOVIE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView
    private Long movieId;
    
     //title, description, at least one image, stock, rental price, sale price and availability.
    
    @NotBlank
    @JsonView
    private String title;
    
    @NotBlank
    @JsonView
    private String description;
      
    @NotBlank
    @JsonView
    private String imageUrl;  
    
    @JsonView
    private Long likes;
    
    @JsonView
    Integer stock;
    
    @JsonView
    BigDecimal rentalPrice;
    
    
    @JsonView
    BigDecimal salePrice;
    
    
    @JsonView
    Boolean isAvailable;
    
    @Column(nullable=false, updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;
    
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;
    
 
    
}
