/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.controller;

import com.cineclub.rental.dto.AppUserRolesDTO;
import com.cineclub.rental.dto.PasswordForgotDTO;
import com.cineclub.rental.dto.RegisterUserDTO;
import com.cineclub.rental.dto.ResetPasswordDTO;
import com.cineclub.rental.exception.ResourceNotFoundException;
import com.cineclub.rental.exception.TokenExpiredException;
import com.cineclub.rental.model.AppPasswordResetToken;
import com.cineclub.rental.model.AppUser;
import com.cineclub.rental.repository.AppPasswordResetTokenRepository;
import com.cineclub.rental.repository.AppUserRepository;
import com.cineclub.rental.utils.AppMail;
import com.cineclub.rental.utils.EmailService;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author keiic
 */
@RestController
@RequestMapping("/api")
public class AppUserController {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppPasswordResetTokenRepository appPasswordResetTokenRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity processForgotPassword(@Valid @RequestBody PasswordForgotDTO form, HttpServletRequest request) {

        AppUser userId = appUserRepository.findByEmail(form.getEmail()).orElseThrow(() -> new ResourceNotFoundException("Forgot Password", "email", form.getEmail()));

        AppPasswordResetToken token = new AppPasswordResetToken();
        token.setToken(UUID.randomUUID().toString());
        token.setUserId(userId);
        token.setExpiryDate(30);
        appPasswordResetTokenRepository.save(token);

        AppMail mail = new AppMail();
        mail.setFrom("no-reply@cineclub.com");
        mail.setTo(userId.getEmail());
        mail.setSubject("Password reset requested");

        Map<String, Object> model = new HashMap<>();
        model.put("token", token);
        model.put("user", userId);
        model.put("signature", "The team at Cineclub.com");
        //to be portable the interface must set the password reset front-end
        model.put("resetUrl", form.getUrl() + token.getToken());
        mail.setModel(model);
        emailService.sendEmail(mail,"reset-password-template");

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity processResetPassword(@Valid @RequestBody ResetPasswordDTO form) {

        AppPasswordResetToken token = appPasswordResetTokenRepository.findByToken(form.getToken()).orElseThrow(() -> new ResourceNotFoundException("Reset Password", "token", form.getToken()));
        if (!token.isExpired()) {
            AppUser userId = token.getUserId();
            String updatedPassword = passwordEncoder.encode(form.getNewPassword());
            userId.setPassword(updatedPassword);
            appUserRepository.save(userId);
            appPasswordResetTokenRepository.delete(token);

            return ResponseEntity.noContent().build();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
            throw new TokenExpiredException(sdf.format(token.getCreatedAt()));
        }

    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegisterUserDTO form) {
         AppUser newUser = AppUser.builder()
                .username(form.getUsername())
                .password(passwordEncoder.encode(form.getPassword()))
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail())
                .roles(Arrays.asList("ROLE_USER"))
                .build();
        newUser = appUserRepository.save(newUser);
        
        AppMail mail = new AppMail();
        mail.setFrom("no-reply@cineclub.com");
        mail.setTo(form.getEmail());
        mail.setSubject("Welcome to Cineclub!");
        
        Map<String, Object> model = new HashMap<>();
        model.put("user", newUser);
        model.put("signature", "The team at Cineclub.com");
        //to be portable the interface must set the password reset front-end
        mail.setModel(model);
        emailService.sendEmail(mail,"welcome-template");

        
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/roles/{username}")
    public ResponseEntity getRolesByUsername(@PathVariable("username")String username){
        AppUser userId = appUserRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Get Roles By User", "username",username));
        
        return ResponseEntity.ok(userId.getRoles());
    } 
    
    @PutMapping("/roles/{username}")
    public ResponseEntity setRolesByUsername(@PathVariable("username")String username, @Valid @RequestBody AppUserRolesDTO form ){
        AppUser userId = appUserRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Set Roles By User", "username",username));
        
        List<String> roles = userId.getRoles();
        if(!roles.contains(form.getRole())){
            roles.add(form.getRole());
            userId.setRoles(roles);
            appUserRepository.save(userId);
        }
        
        return ResponseEntity.ok(userId.getRoles());
    } 
    
    @DeleteMapping("/roles/{username}")
    public ResponseEntity deleteRolesByUsername(@PathVariable("username")String username, @Valid @RequestBody AppUserRolesDTO form ){
        AppUser userId = appUserRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("Delete Roles By User", "username",username));
        
        List<String> roles = userId.getRoles();
        if(roles.contains(form.getRole())){
            roles.remove(form.getRole());
            userId.setRoles(roles);
            appUserRepository.save(userId);
        }
        
        return ResponseEntity.ok(userId.getRoles());
    } 

}
