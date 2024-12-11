package com.graduationproject.controllers;

import com.graduationproject.DTOs.TripDTO;
import com.graduationproject.services.TripManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/commuter/management")
public class TripManagementController {

    private final TripManagementService tripManagementService;

    @PostMapping("create-update/trip")
    public ResponseEntity<Object> postOrUpdateTrip(@RequestBody TripDTO tripDTO) {
        return tripManagementService.postOrUpdateTrip(tripDTO);
    }

    @DeleteMapping("delete-trip/{tripId}")
    public ResponseEntity<Object> deleteTripById(@PathVariable int tripId) {
        return tripManagementService.deleteTrip(tripId);
    }

    @PostMapping("cancel-trip/{tripId}")
    public ResponseEntity<Object> cancelTrip(@PathVariable Integer tripId) {
        return tripManagementService.cancelTrip(tripId);
    }
}
