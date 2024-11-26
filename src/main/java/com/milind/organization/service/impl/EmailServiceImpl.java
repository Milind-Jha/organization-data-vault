package com.milind.organization.service.impl;

import com.milind.organization.dao.OrganizationWithOtpDao;
import com.milind.organization.entity.Organization;
import com.milind.organization.entity.OrganizationwithOtp;
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
    @Autowired
    OrganizationWithOtpDao organizationWithOtpDao;

    @Override
    public void sendSimpleMail(String organizationId,String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        String otp = generateOtp();
        message.setSubject(subjectBody);
        message.setText(mailBody1+mailBody2+otp);
        message.setFrom("jhamiind88@gmail.com");
        OrganizationwithOtp organizationwithOtp = new OrganizationwithOtp(organizationId,otp);
        organizationWithOtpDao.save(organizationwithOtp);
        mailSender.send(message);
    }

    @Override
    public boolean valiDateOtp() {
        return false;
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
