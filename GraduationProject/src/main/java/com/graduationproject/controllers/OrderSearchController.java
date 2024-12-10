package com.graduationproject.controllers;

import com.graduationproject.services.OrderSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders/search")
@RequiredArgsConstructor
public class OrderSearchController {
    private final OrderSearchService orderSearchService;

    @GetMapping("/{from}/{to}")
    public ResponseEntity<Object> searchOrder(@PathVariable String from, @PathVariable String to) {
        return orderSearchService.searchForOrder(from, to);
    }

    @GetMapping("findAll/{userId}")
    public ResponseEntity<?> findAllByUserId(@PathVariable Integer userId) {
        return orderSearchService.findAllByUserId(userId);
    }

    @GetMapping("find-not-active/{userId}")
    public ResponseEntity<Object> findNotActiveOrders(@PathVariable Integer userId) {
        return orderSearchService.findNotActiveOrders(userId);
    }

    @GetMapping("findOrderById/{orderId}")
    public ResponseEntity<?> findOrderById(@PathVariable Integer orderId) {
        return orderSearchService.findById(orderId);
    }

    @PostMapping("findCommuterOrders/{commuterId}")
    public ResponseEntity<?> findCommuterOrders(@PathVariable Integer commuterId){
        return orderSearchService.findCommuterOrders(commuterId);
    }
}

