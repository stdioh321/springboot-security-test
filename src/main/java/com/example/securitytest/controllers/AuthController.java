package com.example.securitytest.controllers;


import com.example.securitytest.auth.JwtTokenService;
import com.example.securitytest.dtos.auth.LoginRequestDto;
import com.example.securitytest.dtos.auth.LoginResponseDto;
import com.example.securitytest.dtos.user.UserCreateForm;
import com.example.securitytest.models.User;
import com.example.securitytest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequestMapping("/auth")
@RestController
@CrossOrigin
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        var userPass = new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());
        var auth = this.authenticationManager.authenticate(userPass);

        String token = this.jwtTokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserCreateForm registerRequestDto) {
        if (this.userService.getUserByUsername(registerRequestDto.getUsername()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerRequestDto.getPassword());
        registerRequestDto.setPassword(encryptedPassword);
        var user = registerRequestDto.toEntity();
        this.userService.post(user);
        return ResponseEntity.status(201).build();
    }
}