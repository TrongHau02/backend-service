package com.javabackend.controller;

import com.javabackend.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j(topic = "EMAIL-CONTROLLER")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("/send-email")
    public void send(@RequestParam String to, @RequestParam String subject, @RequestParam String content){
        log.info("Sending email to: {}", to);
        this.emailService.send(to, subject, content);
        log.info("Email sent");
    }
}
