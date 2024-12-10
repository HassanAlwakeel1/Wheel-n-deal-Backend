package com.graduationproject.services;

import com.graduationproject.DTOs.NegotiationDTO;
import org.springframework.http.ResponseEntity;

public interface OrderNegotiationService {
    ResponseEntity<?> negotiate(NegotiationDTO negotiationDTO);
}
