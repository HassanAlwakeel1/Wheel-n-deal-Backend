package com.graduationproject.services;

import org.springframework.http.ResponseEntity;

public interface OrderAssignmentService {
    ResponseEntity<?> assignExistingOrder(Integer orderId, Integer tripId);
}