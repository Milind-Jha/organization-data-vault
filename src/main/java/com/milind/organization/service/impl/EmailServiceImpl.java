package com.milind.organization.service.impl;

import com.milind.organization.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${welcome.email.subject}")
    private String subjectBody;
    @Value("${welcome.email.body1}")
    private String mailBody1;
    @Value("${welcome.email.body2}")
    private String mailBody2;
    @Override
    public void sendSimpleMail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subjectBody);
        message.setText(mailBody1+mailBody2+generateOtp());
        message.setFrom("jhamiind61@gmail.com");
        mailSender.send(message);
    }

    private String generateOtp(){
        int otp = new Random().nextInt(9999);
        if(otp<10)
            return "000"+otp;
        else if(otp<100)
            return "00"+otp;
        else if (otp<1000) {
            return "0"+otp;
        }
        else
            return String.valueOf(otp);
    }
}
