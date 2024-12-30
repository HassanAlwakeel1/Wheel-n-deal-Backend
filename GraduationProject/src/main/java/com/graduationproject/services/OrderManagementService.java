package com.graduationproject.services;

import org.springframework.http.ResponseEntity;

public interface OrderManagementService {
    ResponseEntity<?> cancelOrder(Integer orderId, Integer cancelerId);
    ResponseEntity<?> declineOrder(Integer orderId);
    ResponseEntity<?> confirmOrder(Integer orderId);
}