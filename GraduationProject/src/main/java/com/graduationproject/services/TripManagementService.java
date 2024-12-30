package com.graduationproject.services;

import com.graduationproject.DTOs.TripDTO;
import org.springframework.http.ResponseEntity;

public interface TripManagementService {
    ResponseEntity<Object> postOrUpdateTrip(TripDTO tripDTO);
    ResponseEntity<Object> deleteTrip(Integer tripId);
    ResponseEntity<Object> cancelTrip(Integer tripId);
}