package com.example.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController is a REST controller responsible for handling secure requests.
 * It provides an endpoint to verify if a user is authenticated.
 *
 * The controller is annotated with @RestController, making it a specialized component
 * for handling HTTP requests and returning JSON responses.
 */
@RestController
public class TestController {
    @GetMapping("/secure")
    public String secure() {
        return "You are authenticated!";
    }
}
