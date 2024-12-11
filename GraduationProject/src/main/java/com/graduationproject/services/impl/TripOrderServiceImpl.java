package com.graduationproject.services.impl;

import com.graduationproject.entities.Order;
import com.graduationproject.entities.Trip;
import com.graduationproject.repositories.TripRepository;
import com.graduationproject.services.TripOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripOrderServiceImpl implements TripOrderService {

    private final TripRepository tripRepository;

    @Override
    public ResponseEntity<Object> findTripOrders(Integer tripId) {
        if (tripId == null || tripId <= 0) {
            return new ResponseEntity<>(
                    "Invalid tripId provided",
                    HttpStatus.BAD_REQUEST
            );
        }

        try {
            Optional<Trip> tripOptional = tripRepository.findById(tripId);

            if (tripOptional.isEmpty()) {
                return new ResponseEntity<>(
                        "Trip not found with ID: " + tripId,
                        HttpStatus.NOT_FOUND
                );
            }

            Trip trip = tripOptional.get();
            List<Order> orders = trip.getOrders();

            if (orders.isEmpty()) {
                return new ResponseEntity<>(
                        "No orders found for the trip with ID: " + tripId,
                        HttpStatus.NOT_FOUND
                );
            }

            return new ResponseEntity<>(
                    orders,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "An error occurred while retrieving orders for the trip.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
