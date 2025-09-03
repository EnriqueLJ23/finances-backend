package com.jasso.finance.finance.rest;

import com.jasso.finance.finance.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    //field for the auth service
    private AuthService authService;

    @Autowired
    public AuthController(AuthService theAuthService){authService=theAuthService;}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse response = authService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Return a proper error response for authentication failures
            String errorMessage = "Invalid username or password";
            if (e.getMessage().contains("Bad credentials")) {
                errorMessage = "Invalid username or password";
            } else if (e.getMessage().contains("User not found")) {
                errorMessage = "User not found";
            }
            return ResponseEntity.badRequest().body(new ErrorResponse(errorMessage));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        try {
            AuthResponse response = authService.register(registerRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Return a proper error response
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }




}
