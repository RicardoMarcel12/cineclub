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
import java.util.Calendar;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "APP_PASSWORD_RESET_TOKEN")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)

public class AppPasswordResetToken implements Serializable {

    @Id
    @Column(name = "TOKEN_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView
    private Long tokenId;

    private String token;

    @OneToOne(fetch = FetchType.EAGER)
    @Getter(onMethod = @__(
            @JsonIgnore))
    @JoinColumns({
        @JoinColumn(name = "user_id", referencedColumnName = "user_id")})
    AppUser userId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiryDate;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    public void setExpiryDate(Integer minutes) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, minutes);
        this.expiryDate = now.getTime();
    }

    public Boolean isExpired() {
        return new Date().after(this.expiryDate);
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
