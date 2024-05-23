package com.example.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    String fromMailAddress;

    public void send(String toAccount, String subject, String text) {
        /*SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromMailAddress);
        msg.setTo(toAccount);
        msg.setSubject(subject);
        msg.setText(text);
        javaMailSender.send(msg);*/
        try {
            MimeMessage msg = javaMailSender.createMimeMessage();
            msg.setFrom(fromMailAddress);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(msg, true);
            helper.setTo(toAccount);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(msg);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
