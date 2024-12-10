package com.graduationproject.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.graduationproject.DTOs.paymobPaymentDTOs.PayResponseDTO;
import com.graduationproject.DTOs.paymobPaymentDTOs.SecondRequest;
import com.graduationproject.DTOs.paymobPaymentDTOs.ThirdRequest;
import com.graduationproject.DTOs.paymobPaymentDTOs.WalletRequest;
import org.springframework.http.ResponseEntity;

public interface PaymobService {
    String getAuthToken(String apiKey) throws JsonProcessingException;
    String createEcommerceOrder(SecondRequest secondRequest) throws JsonProcessingException;
    String sendPaymentKeyRequest(ThirdRequest thirdRequest) throws JsonProcessingException;
    void savePayResponse(PayResponseDTO payResponse);
    ResponseEntity<?> sendPaymentRequest(WalletRequest walletRequest);
}
