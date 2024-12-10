package com.graduationproject.services;

import com.graduationproject.DTOs.optDTOs.OtpValidationRequest;
import org.springframework.http.ResponseEntity;

public interface SmsService {
    ResponseEntity<Object> forgetPassword(String phoneNumber, String newPassword);
    ResponseEntity<Object> sendSMS(String phoneNumber);
    ResponseEntity<Object> validateOtp(OtpValidationRequest otpValidationRequest);

}
