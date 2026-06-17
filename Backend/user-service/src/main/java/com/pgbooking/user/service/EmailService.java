package com.pgbooking.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendOtp(String toEmail, String otp){

        SimpleMailMessage message = new SimpleMailMessage();


        message.setFrom("remigiusmarioe.07@gmail.com");
        message.setTo(toEmail);
        message.setSubject("PG Booking OTP Verification");
        message.setText("Your OTP is: " + otp + "\n\nValid for 5 minutes");

        mailSender.send(message);
        log.info("OTP verification email sent to mail:" + toEmail );
    }

}
