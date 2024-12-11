package com.graduationproject.controllers;

import com.graduationproject.services.TripOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/commuter/orders")
public class TripOrderController {

    private final TripOrderService tripOrderService;

    @GetMapping("find-trip-orders/{tripId}")
    public ResponseEntity<Object> findTripOrders(@PathVariable Integer tripId) {
        return tripOrderService.findTripOrders(tripId);
    }
}
