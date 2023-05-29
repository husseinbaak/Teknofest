package com.example.tradensbackendv2.config.service;

import com.example.tradensbackendv2.config.JwtService;
import com.example.tradensbackendv2.dtos.JwtDto;
import com.example.tradensbackendv2.dtos.LoginRequestDto;
import com.example.tradensbackendv2.dtos.RegisterRequestDto;
import com.example.tradensbackendv2.exception.RegisteredUserException;
import com.example.tradensbackendv2.models.Role;
import com.example.tradensbackendv2.models.User;
import com.example.tradensbackendv2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @SneakyThrows
    public void register(RegisterRequestDto requestDto) {
        if (userRepository.existsByUserName(requestDto.getUserName())) {
            throw new RegisteredUserException("Bu kullanici adına sahip kullanici bulunmakta lütfen farklı kullanıcı adı seçiniz!!");
        } else {
            var user = User.builder()
                    .userName(requestDto.getUserName())
                    .name(requestDto.getName())
                    .surname(requestDto.getSurname())
                    .password(passwordEncoder.encode(requestDto.getPassword()))
                    .role(Role.USER)
                    .dateOfRegistration(new Timestamp(System.currentTimeMillis()))
                    .build();
            userRepository.save(user);
        }
    }

    public JwtDto login(LoginRequestDto login) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUserName(), login.getPassword()));

        var user = userRepository.findByUserName(login.getUserName()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return JwtDto.builder()
                .token(jwtToken)
                .build();
    }
}