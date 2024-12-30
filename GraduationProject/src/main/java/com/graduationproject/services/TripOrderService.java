package com.graduationproject.services;

import org.springframework.http.ResponseEntity;

public interface TripOrderService {
    ResponseEntity<Object> findTripOrders(Integer tripId);
}