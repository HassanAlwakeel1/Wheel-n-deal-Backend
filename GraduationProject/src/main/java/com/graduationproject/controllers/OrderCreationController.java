package com.graduationproject.controllers;

import com.graduationproject.DTOs.OrderDTO;
import com.graduationproject.services.OrderCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderCreationController {
    private final OrderCreationService orderCreationService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("create-update")
    public ResponseEntity<Object> createOrder(@ModelAttribute OrderDTO orderDTO) {
        // Create or update the order
        ResponseEntity<Object> response = orderCreationService.createOrUpdateOrder(orderDTO);

        // Notify all commuters about the new order only if the order creation was successful
        if (response.getStatusCode().is2xxSuccessful()) {
            messagingTemplate.convertAndSend("/commuters", "New order posted: " + orderDTO.getDetails());
        }

        return response;
    }

    @PostMapping("create-and-assign/{tripId}")
    public ResponseEntity<Object> createOrderAndAssignIt(@ModelAttribute OrderDTO orderDTO, @PathVariable Integer tripId) {
        return orderCreationService.createOrderAndAssignIt(orderDTO, tripId);
    }
}
