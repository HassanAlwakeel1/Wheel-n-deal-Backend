package com.graduationproject.controllers;

import com.graduationproject.services.TripSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/commuter/search")
public class TripSearchController {

    private final TripSearchService tripSearchService;

    @GetMapping("find-all-trips-by-id/{commuterId}")
    public ResponseEntity<Object> findAllTripsById(@PathVariable Integer commuterId) {
        return tripSearchService.findCommuterTrips(commuterId);
    }

    @PostMapping("search-for-trip/{from}/{to}")
    public ResponseEntity<Object> searchForTrip(@PathVariable String from, @PathVariable String to) {
        return tripSearchService.searchForTrip(from, to);
    }
}
