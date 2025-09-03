package com.jasso.finance.finance.service;

import com.jasso.finance.finance.dao.RoleRepository;
import com.jasso.finance.finance.dao.UserRepository;
import com.jasso.finance.finance.entity.Role;
import com.jasso.finance.finance.entity.User;
import com.jasso.finance.finance.rest.AuthController;
import com.jasso.finance.finance.rest.AuthResponse;
import com.jasso.finance.finance.rest.LoginRequest;
import com.jasso.finance.finance.rest.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthService {
    //field for the JwtService
    private final JwtService jwtService;
    //field for the DAO for the User
    private final UserRepository userRepository;
    //field for role repository
    private final RoleRepository roleRepository;
    //field for password encoder
    private final PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;

    //inject the jwt service and the user repository
    @Autowired
    public AuthService(UserRepository theUserRepository, JwtService theJwtService,  PasswordEncoder thePasswordEncoder, RoleRepository theRoleRepository, AuthenticationManager theAuthenticationManager) {
        userRepository = theUserRepository;
        jwtService = theJwtService;
        passwordEncoder = thePasswordEncoder;
        roleRepository = theRoleRepository;
        authenticationManager = theAuthenticationManager;
    }

    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();
        String token = jwtService.getToken(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setId(user.getId());
        authResponse.setUsername(user.getUsername());
        authResponse.setExpiresAt(jwtService.getTokenExpiration());
        return authResponse;
    }


    public AuthResponse register(RegisterRequest registerRequest) {
        // Check if user already exists
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(Set.of(userRole));

        userRepository.save(user);

        AuthResponse response = new AuthResponse();
        response.setToken(jwtService.getToken(user));
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setExpiresAt(jwtService.getTokenExpiration());
        return response;
    }

}
