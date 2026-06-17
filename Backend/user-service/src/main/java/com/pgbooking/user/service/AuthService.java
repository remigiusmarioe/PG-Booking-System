package com.pgbooking.user.service;

import com.pgbooking.user.dto.*;
import com.pgbooking.user.entity.OtpVerification;
import com.pgbooking.user.entity.User;
import com.pgbooking.user.exception.*;
import com.pgbooking.user.repository.OtpRepository;
import com.pgbooking.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;



@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final OtpRepository otpRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public RegisterResponse register(RegisterRequest request){

        log.info("Register request received for email: {}", request.getEmail());

        //Checking Email is Already exists
        if (userRepository.existsByEmail(request.getEmail())){

            log.warn("Email already registered: {}", request.getEmail());
            throw  new UserAlreadyExistsException("Email already Registered");
        }
        //Checking mobileNo is Already exists
        if (userRepository.existsByMobileNo(request.getMobileN0())){
            log.warn("Mobile number already registered: {}", request.getMobileN0());
            throw  new UserAlreadyExistsException("Mobile number already Registered");
        }
        // Validating password and confirmpassword match
        if(!request.getPassword().equals(request.getConfirmPassword())){
            log.warn("Passwords don't match");
            throw  new InvalidCredentialsException("Passwords don't match");
        }
        log.info("Generating Otp for email: {}", request.getEmail());

        //Generating Otp
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000,999999));



        // Create Otp Entity and set expiry time to 5 min
        OtpVerification otpVerification = OtpVerification.builder()
                .email(request.getEmail())
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .build();
        // store otp details in Db
        otpRepository.save(otpVerification);

        log.info("Otp saved in Db for email: {}", request.getEmail());

        //Sending Otp to User's Mail
        emailService.sendOtp(request.getEmail(), otp);

        log.info("Otp sent to email: {}", request.getEmail());


        //Save user with unverified status untill Otp Verification
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobileNo(request.getMobileN0())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .verified(false)
                .build();
        userRepository.save(user);

        log.info("User saved in Db for email: {}", request.getEmail()+"With verification = false");

        // Return success response to client
        return new  RegisterResponse("OTP sent sucessfully ", true);

    }

    public RegisterResponse verifyOtp(OtpVerifyRequest request){

        log.info(" Otp verification  request received for email: {}", request.getEmail());

        //Otp Verification
        OtpVerification otpData = otpRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Otp Not Found"));

        log.info("Otp record found for email: {}", request.getEmail());

        // Checking otp entered is same as otp stored in Db
        if (!otpData.getOtp().equals(request.getOtp())){
            log.warn("invalid Otp entered for email: {}", request.getEmail());

            throw  new InvalidOtpException("Invalid OTP");
        }
       // Checking expiry time
        if (otpData.getExpiryTime().isBefore(LocalDateTime.now())){

            log.warn("Otp expired for email: {}", request.getEmail());
            throw  new OtpExpiredException("OTP Expired");
        }

        otpData.setVerified(true);
        otpRepository.save(otpData);

        // Otp is Verified And User is Verified
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setVerified(true);
        userRepository.save(user);
        log.info("User verification Updated to true for email: {}", request.getEmail());

        //Return Success message to client
        return new  RegisterResponse("OTP Verified successfully ", true);

    }

    public LoginResponse login(LoginRequest request){
        log.info("Login request received for email: {}", request.getEmail());
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        if (!user.getVerified()){
            log.warn("Invalid Email or password");
            throw  new InvalidCredentialsException("Please Verify your Email First");
        }

        if (!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw  new InvalidCredentialsException("Invalid Email or password");
        }

        // Generate Jwt token for authenticate user
        String token = jwtService.generateToken(user.getEmail(),user.getRole());
        log.info("JWT token generated for email: {}", user.getEmail() + "Token = " + token);

        log.info("Login successful for email: {}", user.getEmail());

        // Return login response with jwt token
        return  LoginResponse.builder()
                .message("Login Successful")
                .success(true)
                .token(token)
                .build();

    }

}
