package com.example.oauth2.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class MyController {

    @GetMapping("/")
    public String index() {
        return "Hello";
    }
    
}
