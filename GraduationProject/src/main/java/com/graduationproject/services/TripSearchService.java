package com.graduationproject.services;

import org.springframework.http.ResponseEntity;

public interface TripSearchService {
    ResponseEntity<Object> searchForTrip(String from, String to);
    ResponseEntity<Object> findCommuterTrips(Integer commuterId);
}
