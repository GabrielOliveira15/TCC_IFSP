package com.gabrieloliveira.springsecurity.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gabrieloliveira.springsecurity.Controller.dto.EmailDTO;
import com.gabrieloliveira.springsecurity.config.EmailSender;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailSender emailSender;

    public EmailController(EmailSender emailSender) {
        this.emailSender = emailSender;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void enviarEmail(@RequestBody EmailDTO emailDTO) {
        emailSender.sendEmail(emailDTO);
    }
    
}
