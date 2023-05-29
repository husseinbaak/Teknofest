package com.example.tradensbackendv2.controllers;

import com.example.tradensbackendv2.config.service.AuthenticationService;
import com.example.tradensbackendv2.dtos.JwtDto;
import com.example.tradensbackendv2.dtos.LoginRequestDto;
import com.example.tradensbackendv2.dtos.RegisterRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arac/guvenlik")
@RequiredArgsConstructor
public class SecurityController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto requestDto){
        authenticationService.register(requestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginRequestDto login){
        return new ResponseEntity<>(authenticationService.login(login),HttpStatus.OK);
    }
}