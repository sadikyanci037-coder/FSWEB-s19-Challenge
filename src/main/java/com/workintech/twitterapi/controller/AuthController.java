package com.workintech.twitterapi.controller;

import com.workintech.twitterapi.entity.User;
import com.workintech.twitterapi.exceptions.ApiException;
import com.workintech.twitterapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(
            UserService userService,
            PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @Valid @RequestBody User user) {

        if (userService.findByUsername(user.getUsername()).isPresent()) {
            throw new ApiException(
                    "Bu kullanıcı adı zaten kullanılıyor.",
                    HttpStatus.BAD_REQUEST
            );
        }

        if (userService.findByEmail(user.getEmail()).isPresent()) {
            throw new ApiException(
                    "Bu email zaten kullanılıyor.",
                    HttpStatus.BAD_REQUEST
            );
        }

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        User savedUser = userService.save(user);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestBody User loginRequest) {

        User user = userService.findByUsername(loginRequest.getUsername())
                .orElseThrow(() ->
                        new ApiException(
                                "Kullanıcı bulunamadı.",
                                HttpStatus.UNAUTHORIZED
                        )
                );

        if (!passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword())) {

            throw new ApiException(
                    "Şifre hatalı.",
                    HttpStatus.UNAUTHORIZED
            );
        }

        return ResponseEntity.ok("Login başarılı.");
    }
}