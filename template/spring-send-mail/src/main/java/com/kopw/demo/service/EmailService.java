package com.kopw.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

//    public void sendSimpleEmail(String toEmail, String subject, String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(toEmail);
//        message.setSubject(subject);
//        message.setText(body);
//        message.setFrom("sanha6181@gmail.com"); //
//
//        mailSender.send(message);
//        System.out.println("Email sent successfully.");
//    }

    public void sendThymeleafEmail(String toEmail, String subject, String name) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        Context context = new Context();
        context.setVariable("name", name);
        String htmlBody = templateEngine.process("emailTemplate", context);

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlBody, true); // `true` để cho phép HTML
        helper.setFrom("your-email@gmail.com");

        mailSender.send(mimeMessage);
        System.out.println("Thymeleaf email sent successfully.");
    }
}