package com.graduationproject.services;

import com.graduationproject.DTOs.stripePaymentDTOs.ChargeUserDTO;
import org.springframework.http.ResponseEntity;

public interface StripeService {
    ResponseEntity<Object> chargeUser(ChargeUserDTO chargeUserDTO);
    ResponseEntity<Object> getAllUserCharges(String stripeUserId);
}