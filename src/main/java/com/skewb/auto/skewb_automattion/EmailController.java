package com.skewb.auto.skewb_automattion;


import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
public class EmailController {

    private final JavaMailSender mailSender;

    public EmailController(JavaMailSender mailSender){
        this.mailSender= mailSender;
    }

    @RequestMapping("/send-email")
    public String sendemailWithAttachment() {
        try{
            MimeMessage message =mailSender.createMimeMessage();
            MimeMessageHelper helper =new MimeMessageHelper(message,true);

            helper.setFrom("bhuvanvijayan969@gmail.com");
            helper.setTo("bhuvanes0987@gmail.com");
            helper.setSubject("simple test email with attachment");

            try(var inputStream = Objects.requireNonNull(EmailController.class.getResourceAsStream("/templates/email-content.html"))) {
                helper.setText(
                        new String(inputStream.readAllBytes(), StandardCharsets.UTF_8),
                        true
                );
            }

            helper.addAttachment("House_Rent_Dataset.csv", new File("C:\\Users\\Dishanth V\\OneDrive\\Documents\\House_Rent_Dataset.csv.xlsx"));

            mailSender.send(message);
            return "Success";
        } catch (Exception e){
            return e.getMessage();

        }


    }
}

