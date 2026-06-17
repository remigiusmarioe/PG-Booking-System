package com.pgbooking.user.controller;

import com.pgbooking.user.service.EmailService;
import com.pgbooking.user.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class TestController {

    @GetMapping
    public String test(){
        return "Protected Api";
    }

    @GetMapping("/owner/test")
    public  String ownerApi(){
        return "Owner Api";
    }

    @GetMapping("/finder/test")
    public  String finderApi(){
        return "Finder Api";
    }
}
