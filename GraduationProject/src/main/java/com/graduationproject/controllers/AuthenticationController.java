package com.graduationproject.controllers;

import com.graduationproject.DTOs.*;
import com.graduationproject.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("signup")
    public ResponseEntity<?> signup(@Validated @RequestBody SignUpRequest signUpRequest, BindingResult bindingResult) {
        ResponseEntity<?> response = authenticationService.signup(signUpRequest, bindingResult);

        if (response.getStatusCode().is2xxSuccessful()) {
            String username = signUpRequest.getUsername();
            String welcomeMessage = "Welcome, " + username + "! Thank you for signing up.";
            messagingTemplate.convertAndSend("/topic/notifications", welcomeMessage);
        }
        return response;
    }

    @PostMapping("signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest)  {
        ResponseEntity<?> response = authenticationService.signin(signInRequest);

        if (response.getStatusCode().is2xxSuccessful()) {
            String username = signInRequest.getUsername();
            String welcomeMessage = "Welcome, " + username + "!";
            messagingTemplate.convertAndSend("/topic/notifications", welcomeMessage);
        }
        return response;
    }

    @PostMapping("refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest)  {
        return authenticationService.refreshToken(refreshTokenRequest);
    }

}