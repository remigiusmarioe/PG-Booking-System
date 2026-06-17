package com.pgbooking.user.dto;

import com.pgbooking.user.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RegisterRequest {

    @NotBlank
    private String name;

    @Email
    private String email;

    @NotBlank
    private String mobileN0;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    private Role role;

}
