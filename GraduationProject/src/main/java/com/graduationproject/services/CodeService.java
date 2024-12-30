package com.graduationproject.services;

import org.springframework.http.ResponseEntity;

public interface CodeService {
    void generateCode(Integer orderId);
    ResponseEntity<?> checkSenderCode(Integer orderId, String enteredCode);
    ResponseEntity<?> checkReceiverCode(Integer orderId, String enteredCode);
    ResponseEntity<?> checkFailureCode(Integer orderId, String enteredCode);
    ResponseEntity<?> getSenderCode(Integer orderId);
    ResponseEntity<?> getReceiverCode(Integer orderId);
}