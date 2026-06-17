package com.pgbooking.user.service;

import com.pgbooking.user.entity.OtpVerification;
import com.pgbooking.user.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {

    private  final OtpRepository otpRepository;

    public String generateOtp(){
        Random random = new Random();

        int otp = 100000+ random.nextInt(900000);

        return String.valueOf(otp);
    }

    public void saveOtp(String email, String otp){

        OtpVerification otpVerification = OtpVerification.builder()
                .email(email)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .build();

        otpRepository.save(otpVerification);

    }
}
