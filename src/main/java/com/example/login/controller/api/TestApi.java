package com.example.login.controller.api;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestApi {

    @GetMapping
    public String test() {
        return "Test Authorized";
    }

    @GetMapping("/sendHeaders")
    public ResponseEntity<String> sendHeaders() {
        // สร้าง HttpHeaders object
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "CustomHeaderValue");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer your_token_here");

        // ใช้ ResponseEntity เพื่อส่ง response พร้อมกับ headers
//        return new ResponseEntity<>("Response with custom header", headers, HttpStatus.OK);
        return ResponseEntity.ok()
                .headers(headers)
                .body("Test");
    }
}
