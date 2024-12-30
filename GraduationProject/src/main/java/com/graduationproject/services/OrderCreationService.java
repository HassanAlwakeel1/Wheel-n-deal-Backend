package com.graduationproject.services;

import com.graduationproject.DTOs.OrderDTO;
import org.springframework.http.ResponseEntity;

public interface OrderCreationService {
    ResponseEntity<Object> createOrUpdateOrder(OrderDTO orderDTO);
    ResponseEntity<Object> createOrderAndAssignIt(OrderDTO orderDTO, Integer tripId);
}