package br.isertech.com.easyfinancesback.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping
    public ResponseEntity<String> getTest() {
        return ResponseEntity.status(HttpStatus.OK).body(LocalDateTime.now().toString());
    }
}