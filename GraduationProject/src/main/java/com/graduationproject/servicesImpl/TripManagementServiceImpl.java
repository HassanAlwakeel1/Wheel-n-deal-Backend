package com.graduationproject.servicesImpl;

import com.graduationproject.DTOs.TripDTO;
import com.graduationproject.entities.Trip;
import com.graduationproject.entities.User;
import com.graduationproject.repositories.TripRepository;
import com.graduationproject.repositories.UserRepository;
import com.graduationproject.services.OrderManagementService;
import com.graduationproject.services.TripManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripManagementServiceImpl implements TripManagementService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final OrderManagementService orderManagementService;

    @Override
    public ResponseEntity<Object> postOrUpdateTrip(TripDTO tripDTO) {
        if (tripDTO == null) {
            return new ResponseEntity<>(
                    Map.of("status", HttpStatus.BAD_REQUEST.value(), "message", "Trip data cannot be null."),
                    HttpStatus.BAD_REQUEST
            );
        }

        try {
            if (tripDTO.getId() != null) {
                Optional<Trip> optionalTrip = tripRepository.findById(tripDTO.getId());
                if (optionalTrip.isPresent()) {
                    Trip existingTrip = optionalTrip.get();
                    updateTripFromDTO(existingTrip, tripDTO);
                    tripRepository.save(existingTrip);
                    return new ResponseEntity<>(
                            Map.of("status", HttpStatus.OK.value(), "message", "Trip updated successfully"),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            Map.of("status", HttpStatus.NOT_FOUND.value(), "message", "Trip not found with ID: " + tripDTO.getId()),
                            HttpStatus.NOT_FOUND
                    );
                }
            } else {
                saveNewTripFromDTO(tripDTO);
                return new ResponseEntity<>(
                        Map.of("status", HttpStatus.CREATED.value(), "message", "Trip created successfully"),
                        HttpStatus.CREATED
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("status", HttpStatus.INTERNAL_SERVER_ERROR.value(), "message", "An error occurred while processing the trip."),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    private Trip saveNewTripFromDTO(TripDTO tripDTO) {
        Optional<User> optionalUser = userRepository.findById(tripDTO.getUserId());
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with ID: " + tripDTO.getUserId());
        }
        Trip trip = new Trip();
        updateTripFromDTO(trip, tripDTO);
        User user = optionalUser.get();
        trip.setUser(user);
        return tripRepository.save(trip);
    }

    private void updateTripFromDTO(Trip trip, TripDTO tripDTO) {
        trip.setFrom(tripDTO.getFrom());
        trip.setTo(tripDTO.getTo());
        trip.setPaths(tripDTO.getPaths());
        trip.setDay(tripDTO.getDay());
        trip.setStartsAt(tripDTO.getStartsAt());
        trip.setEndsAt(tripDTO.getEndsAt());
        trip.setCapacity(tripDTO.getCapacity());
    }

    @Override
    public ResponseEntity<Object> deleteTrip(Integer tripId) {
        if (tripId <= 0) {
            return new ResponseEntity<>(
                    Map.of("status", 400, "message", "Invalid tripId provided"),
                    HttpStatus.BAD_REQUEST
            );
        }

        try {
            tripRepository.deleteById(tripId);
            return new ResponseEntity<>(
                    Map.of("status", 200, "message", "Trip deleted successfully"),
                    HttpStatus.OK
            );

        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(
                    Map.of("status", 404, "message", "Trip not found with ID: " + tripId),
                    HttpStatus.NOT_FOUND
            );

        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("status", 500, "message", "An error occurred while deleting the trip"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseEntity<Object> cancelTrip(Integer tripId) {
        if (tripId == null || tripId <= 0) {
            return new ResponseEntity<>(
                    Map.of("status", 400, "message", "Invalid tripId provided"),
                    HttpStatus.BAD_REQUEST
            );
        }

        try {
            Optional<Trip> optionalTrip = tripRepository.findById(tripId);

            if (!optionalTrip.isPresent()) {
                return new ResponseEntity<>(
                        Map.of("status", 404, "message", "Trip not found with ID: " + tripId),
                        HttpStatus.NOT_FOUND
                );
            }

            Trip trip = optionalTrip.get();
            Integer commuterId = trip.getUser().getId();

            if (trip.getOrders() != null) {
                trip.getOrders().forEach(order -> orderManagementService.cancelOrder(order.getId(), commuterId));
            }

            tripRepository.deleteById(tripId);

            return new ResponseEntity<>(
                    Map.of("status", 200, "message", "Trip canceled successfully"),
                    HttpStatus.OK
            );

        } catch (Exception e) {
            return new ResponseEntity<>(
                    Map.of("status", 500, "message", "An error occurred while canceling the trip"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
