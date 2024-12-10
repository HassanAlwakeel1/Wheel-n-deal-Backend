package com.graduationproject.controllers;

import com.graduationproject.DTOs.OrderDTO;
import com.graduationproject.services.OrderCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderCreationController {
    private final OrderCreationService orderCreationService;

    @PostMapping("create-update")
    public ResponseEntity<Object> createOrder(@ModelAttribute OrderDTO orderDTO) {
        return orderCreationService.createOrUpdateOrder(orderDTO);
    }

    @PostMapping("create-and-assign/{tripId}")
    public ResponseEntity<Object> createOrderAndAssignIt(@ModelAttribute OrderDTO orderDTO, @PathVariable Integer tripId) {
        return orderCreationService.createOrderAndAssignIt(orderDTO, tripId);
    }
}
