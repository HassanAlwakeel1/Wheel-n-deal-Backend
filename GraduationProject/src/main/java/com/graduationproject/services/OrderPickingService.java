package com.graduationproject.services;

import com.graduationproject.DTOs.ConfirmPickingUpDTO;
import com.graduationproject.DTOs.OrderPickDTO;
import org.springframework.http.ResponseEntity;

public interface OrderPickingService {
    ResponseEntity<?> pickOrder(OrderPickDTO orderPickDTO);
    ResponseEntity<?> confirmPickingUp(ConfirmPickingUpDTO confirmPickingUpDTO);
    ResponseEntity<?> getOrderApplicants(Integer orderId);
}
