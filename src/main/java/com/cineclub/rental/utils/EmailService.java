/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cineclub.rental.utils;

import java.nio.charset.StandardCharsets;
import javax.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

/**
 *
 * @author keiic
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
   private SpringTemplateEngine templateEngine;
    
    Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail(AppMail mail,String mailTemplate) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            
            
            String html = build(mail, mailTemplate);
            logger.info(html);
            helper.setTo(mail.getTo());
            helper.setText(html,true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());
            emailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    
    public String build(AppMail mail, String mailTemplate) {
        Context context = new Context();
        context.setVariables(mail.getModel());
        try {
            return templateEngine.process(mailTemplate, context);
            
        } catch (Exception e) {
            logger.equals(e);
            return "<html><head></head><body><h1>ERROR OCURRIO</h1></body><html>";
        }
    }
}
