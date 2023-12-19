package com.example.demo.controller;

import com.example.demo.DTO.CustomerDTO;
import com.example.demo.service.CustomerService;
import com.example.demo.service.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final JwtService jwtService;
    private final CustomerService customerService;

    public AuthController(JwtService jwtService, CustomerService customerService) {
        this.jwtService = jwtService;
        this.customerService = customerService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        try {
            CustomerDTO loggedInCustomer = customerService.login(email, password);
            String token = jwtService.generateToken(email);

            // Формування відповіді разом з токеном
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("loggedInCustomer", loggedInCustomer);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }
}
