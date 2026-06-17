package com.pgbooking.user.security;

import com.pgbooking.user.entity.User;
import com.pgbooking.user.repository.UserRepository;
import com.pgbooking.user.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        log.info("Authorization header: {}", authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Remove Barrer prefix
        String token = authHeader.substring(7);
        log.info("Token Extracted");

        // Extract email from token
        String email = jwtService.extractEmail(token);

        log.info("Extracted email from token : {}", email);

        // Extracted role from token
        String role = jwtService.extractRole(token);
        log.info("Extracted role from token : {}", role);

        List<SimpleGrantedAuthority> authorities =List.of(new SimpleGrantedAuthority("ROLE_"+role));

        User user = userRepository.findByEmail(email).orElse(null);

        if (user != null && jwtService.validateToken(token,email)) {

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user,null,authorities);

            SecurityContextHolder.getContext()
                    .setAuthentication(authToken);
            log.info("User Authenticated successfully");
        }


        filterChain.doFilter(request, response);

    }
}
