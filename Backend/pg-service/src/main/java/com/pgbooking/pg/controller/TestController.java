package com.pgbooking.pg.controller;

import com.pgbooking.pg.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class TestController {
    private final CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        log.info("Received request to upload image file {}", file.getOriginalFilename());
        return cloudinaryService.uploadImage(file);
    }
}
