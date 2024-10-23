package com.example.Automach.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service // This makes the class a Spring-managed bean
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender; // Autowire the JavaMailSender

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to); // Set recipient
        mailMessage.setSubject(subject); // Set subject
        mailMessage.setText(text); // Set message text

        javaMailSender.send(mailMessage); // Send the email
    }
}
