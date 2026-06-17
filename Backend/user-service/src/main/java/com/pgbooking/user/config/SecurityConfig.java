package com.pgbooking.user.config;

import com.pgbooking.user.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {

        return
                http.csrf(csrf -> csrf.disable())
                        .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/register",
                                "/auth/verify-otp","/auth/login")
                                .permitAll()
                                .requestMatchers("/owner/**").hasRole("PG_OWNER")
                                .requestMatchers("/finder/**").hasRole("PG_FINDER")
                                .anyRequest()
                                .authenticated() )

                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
    }
}
