package com.graduationproject.controllers;

import com.graduationproject.services.OrderManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/orders/manage")
@RequiredArgsConstructor
public class OrderManagementController {
    private final OrderManagementService orderManagementService;

    @PostMapping("cancel-order/{orderId}/{cancelerId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Integer orderId, @PathVariable Integer cancelerId) {
        return orderManagementService.cancelOrder(orderId, cancelerId);
    }

    @PostMapping("declineOrder/{orderId}")
    public ResponseEntity<?> declineOrder(@PathVariable Integer orderId) {
        return orderManagementService.declineOrder(orderId);
    }

    @PostMapping("confirmOrder/{orderId}")
    public ResponseEntity<?> confirmOrder(@PathVariable Integer orderId) {
        return orderManagementService.confirmOrder(orderId);
    }
}

