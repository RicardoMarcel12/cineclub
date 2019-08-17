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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
@Table(name = "MR_RENT_BUY_MOVIE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class MrRentBuyMovie implements Serializable {

    @Id
    @Column(name = "RENT_BUY_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView
    private Long rentBuyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter(onMethod = @__(
            @JsonIgnore))
    @JoinColumns({
        @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")})
    MrMovie movieId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter(onMethod = @__(
            @JsonIgnore))
    @JoinColumns({
        @JoinColumn(name = "user_id", referencedColumnName = "user_id")})
    AppUser userId;

    /*true->rent - false->buy*/
    @NotNull
    Boolean isRentBuy;

    /*one by default*/
    @NotNull
    Integer days;

    /*days after rent*/
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    Date returnDate;

    /*date of actual return*/
    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    Date returnedOn;

    /*one by default*/
    @JsonView
    Integer copiesQty;

    @JsonView
    BigDecimal operationAmount;

    /*true->active - false->inactive
     purchases are inactive by default */
    @NotNull
    Boolean isActive;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public Long getMrMovieIdDelegate() {
        if (this.movieId != null) {
            return this.movieId.getMovieId();
        } else {
            return null;
        }
    }

    public String getMrMovieTitleDelegate() {
        if (this.movieId != null) {
            return this.movieId.getTitle();
        } else {
            return null;
        }
    }

    public Long getAppUserIdDelegate() {
        if (this.userId != null) {
            return this.userId.getUserId();
        } else {
            return null;
        }
    }

    public String getAppUserUsernameDelegate() {
        if (this.userId != null) {
            return this.userId.getUsername();
        } else {
            return null;
        }
    }
}
