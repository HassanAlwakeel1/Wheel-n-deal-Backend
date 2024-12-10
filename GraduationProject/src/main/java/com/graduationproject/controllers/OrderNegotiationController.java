package com.graduationproject.controllers;

import com.graduationproject.DTOs.NegotiationDTO;
import com.graduationproject.services.OrderNegotiationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/orders/negotiate")
@RequiredArgsConstructor
public class OrderNegotiationController {
    private final OrderNegotiationService orderNegotiationService;

    @PostMapping
    public ResponseEntity<?> negotiate(@RequestBody NegotiationDTO negotiationDTO) {
        return orderNegotiationService.negotiate(negotiationDTO);
    }
}

