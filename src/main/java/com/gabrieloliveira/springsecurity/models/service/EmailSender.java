package com.gabrieloliveira.springsecurity.models.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.gabrieloliveira.springsecurity.Controller.dto.EmailDTO;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSender {
    
    private final JavaMailSender javaMailSender;

    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    
    public void sendEmail(EmailDTO emailDTO) { // Método para enviar e-mail 

        try { 
            MimeMessage message = javaMailSender.createMimeMessage(); // Criar mensagem de e-mail com HTML
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@demomailtrap.com");
            helper.setTo(emailDTO.to());
            helper.setSubject(emailDTO.subject());
            helper.setText(carregaTemplateEmail(), true);
            javaMailSender.send(message);

        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail: " + e.getMessage());
        }

        /* Enviar mensagem de e-mail
        var message = new SimpleMailMessage();
        message.setFrom("noreply@demomailtrap.com");
        message.setTo(emailDTO.to());
        message.setSubject(emailDTO.subject());
        message.setText(emailDTO.body());
        javaMailSender.send(message);
        */
    }

    private String carregaTemplateEmail() throws IOException {
        ClassPathResource resource = new ClassPathResource("templateEmailConfirmacao.html");
        return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }
     
}
