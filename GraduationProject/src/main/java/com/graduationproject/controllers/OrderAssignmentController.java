package com.graduationproject.controllers;

import com.graduationproject.services.OrderAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/orders/assign")
@RequiredArgsConstructor
public class OrderAssignmentController {
    private final OrderAssignmentService orderAssignmentService;

    @PostMapping("/existing/{orderId}/{tripId}")
    public ResponseEntity<?> assignExistingOrder(@PathVariable Integer orderId, @PathVariable Integer tripId) {
        return orderAssignmentService.assignExistingOrder(orderId, tripId);
    }
}