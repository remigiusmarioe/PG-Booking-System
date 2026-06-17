package com.pgbooking.user.repository;

import com.pgbooking.user.entity.OtpVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpVerification, Long> {

    // This method gives the Latest otp
    Optional<OtpVerification> findTopByEmailOrderByIdDesc(String email);
    Optional<OtpVerification> findByEmail(String email);
}
