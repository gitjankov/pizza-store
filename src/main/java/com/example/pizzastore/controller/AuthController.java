package com.example.pizzastore.controller;

import com.example.pizzastore.dto.*;
import com.example.pizzastore.model.User;
import com.example.pizzastore.repsitory.JwtUserRepository;
import com.example.pizzastore.service.CustomUserDetailsService;
import com.example.pizzastore.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class AuthController {

    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private CustomUserDetailsService userDetailsService;
    private JwtUtil jwtUtil;
    private JwtUserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,PasswordEncoder passwordEncoder,
                          CustomUserDetailsService userDetailsService,JwtUtil jwtUtil,
                          JwtUserRepository userRepository){
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @GetMapping("/checkUser")
    public String checkUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return  currentPrincipalName;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(AuthenticationResponse.builder().token(token).build());

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.findUserByEmail(signUpRequest.getEmail()) != null) {
            log.error("Email Address already in use."+signUpRequest.getEmail() );
            return new ResponseEntity(ApiResponse.builder().success(false).message("Email Address already in use!").build(),
                    HttpStatus.BAD_REQUEST);
        }
        log.info("User registered successfully."+signUpRequest.getEmail());
        User jwtUser = new User();
        jwtUser.setEmail(signUpRequest.getEmail());
        jwtUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userRepository.save(jwtUser);
        return ResponseEntity.ok(ApiResponse.builder().success(true).message("User registered successfully").build());
    }
}
