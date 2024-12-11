package com.graduationproject.services.impl;

import com.graduationproject.entities.Trip;
import com.graduationproject.entities.User;
import com.graduationproject.repositories.TripRepository;
import com.graduationproject.repositories.UserRepository;
import com.graduationproject.services.TripSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripSearchServiceImpl implements TripSearchService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<Object> searchForTrip(String from, String to) {
        if (from == null || from.isEmpty() || to == null || to.isEmpty()) {
            return new ResponseEntity<>(
                    "Invalid search parameters. 'from' and 'to' cannot be empty.",
                    HttpStatus.BAD_REQUEST
            );
        }

        try {
            List<Trip> trips = tripRepository.findByFromAndTo(from, to);

            if (trips.isEmpty()) {
                return new ResponseEntity<>(
                        "No trips found from " + from + " to " + to,
                        HttpStatus.NOT_FOUND
                );
            }

            return new ResponseEntity<>(
                    trips,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "An error occurred while searching for trips.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseEntity<Object> findCommuterTrips(Integer commuterId) {
        if (commuterId == null || commuterId <= 0) {
            return new ResponseEntity<>(
                    "Invalid commuterId provided",
                    HttpStatus.BAD_REQUEST
            );
        }

        try {
            Optional<User> userOptional = userRepository.findById(commuterId);

            if (userOptional.isEmpty()) {
                return new ResponseEntity<>(
                        "Commuter not found with ID: " + commuterId,
                        HttpStatus.NOT_FOUND
                );
            }

            User commuter = userOptional.get();
            List<Trip> trips = tripRepository.findByUser(commuter);

            if (trips.isEmpty()) {
                return new ResponseEntity<>(
                        "No trips found for commuter with ID: " + commuterId,
                        HttpStatus.NOT_FOUND
                );
            }

            return new ResponseEntity<>(
                    trips,
                    HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "An error occurred while retrieving commuter trips.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
