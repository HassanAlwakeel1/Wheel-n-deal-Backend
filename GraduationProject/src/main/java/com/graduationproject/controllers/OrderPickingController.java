package com.graduationproject.controllers;

import com.graduationproject.DTOs.ConfirmPickingUpDTO;
import com.graduationproject.DTOs.OrderPickDTO;
import com.graduationproject.services.OrderPickingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders/pick")
@RequiredArgsConstructor
public class OrderPickingController {
    private final OrderPickingService orderPickingService;

    @PostMapping("pickOrder")
    public ResponseEntity<?> pickOrder(@RequestBody OrderPickDTO orderPickDTO) {
        return orderPickingService.pickOrder(orderPickDTO);
    }

    @GetMapping("getOrderApplicants/{orderId}")
    public ResponseEntity<?> getOrderApplicants(@PathVariable Integer orderId) {
        return orderPickingService.getOrderApplicants(orderId);
    }

    @PostMapping("confirmPickingUp")
    public ResponseEntity<?> confirmPickingUp(@RequestBody ConfirmPickingUpDTO confirmPickingUpDTO) {
        return orderPickingService.confirmPickingUp(confirmPickingUpDTO);
    }
}

