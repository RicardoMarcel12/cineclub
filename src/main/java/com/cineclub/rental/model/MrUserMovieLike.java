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
@Table(name = "MR_USER_MOVIE_LIKE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
public class MrUserMovieLike implements Serializable {

    @Id
    @Column(name = "LIKE_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView
    private Long likeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter(onMethod = @__(
            @JsonIgnore))
    @JoinColumns({
        @JoinColumn(name = "user_id", referencedColumnName = "user_id")})
    AppUser userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @Getter(onMethod = @__(
            @JsonIgnore))
    @JoinColumns({
        @JoinColumn(name = "movie_id", referencedColumnName = "movie_id")})
    MrMovie movieId;

    @NotNull
    Boolean isLiked;

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
