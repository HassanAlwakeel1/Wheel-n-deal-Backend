package com.graduationproject.services;

import com.graduationproject.DTOs.TripDTO;
import org.springframework.http.ResponseEntity;

public interface TripService {
    ResponseEntity<Object> postOrUpdateTrip(TripDTO tripDTO);
    public ResponseEntity<Object> deleteTrip(Integer tripId);
    ResponseEntity<Object> searchForTrip(String from, String to);
    public ResponseEntity<Object> findCommuterTrips(Integer commuterId);
    public ResponseEntity<Object> findTripOrders(Integer tripId);
    public ResponseEntity<Object> cancelTrip(Integer tripId);

}
