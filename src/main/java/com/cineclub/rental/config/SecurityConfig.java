/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.config;

import com.cineclub.rental.security.JwtConfigurer;
import com.cineclub.rental.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author keiic
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    
    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests()
                    .antMatchers("/auth/login").permitAll()
                    .antMatchers(HttpMethod.POST, "/api/forgot-password", "/api/reset-password","/api/register").permitAll()
                    .antMatchers(HttpMethod.GET, "/api/roles/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT, "/api/roles/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/api/roles/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/m1/likes/**").permitAll()
                    .antMatchers(HttpMethod.GET, "/m1/movies/admin","/m1/movies/admin/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/m1/movies/*","/m1/movies").permitAll()
                    .antMatchers(HttpMethod.POST, "/m1/movies/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.DELETE, "/m1/movies/**").hasRole("ADMIN")
                    .antMatchers(HttpMethod.PUT,"/m1/movies/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }
    
}
