package com.graduationproject.servicesImpl;

import com.graduationproject.entities.Order;
import com.graduationproject.enums.OrderStatus;
import com.graduationproject.entities.Trip;
import com.graduationproject.entities.User;
import com.graduationproject.repositories.OrderRepository;
import com.graduationproject.repositories.TripRepository;
import com.graduationproject.services.OrderAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class OrderAssignmentServiceImpl implements OrderAssignmentService {

    private final OrderRepository orderRepository;
    private final TripRepository tripRepository;
    @Override
    public ResponseEntity<?> assignExistingOrder(Integer orderId, Integer tripId) {
        if (orderId == null || orderId <= 0) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", HttpStatus.BAD_REQUEST.value(),
                            "message", "Invalid order ID."
                    )
            );
        }

        if (tripId == null || tripId <= 0) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", HttpStatus.BAD_REQUEST.value(),
                            "message", "Invalid trip ID."
                    )
            );
        }

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);

        if (optionalOrder.isPresent() && optionalTrip.isPresent()) {
            Order existingOrder = optionalOrder.get();
            Trip existingTrip = optionalTrip.get();
            User existingCommuter = existingTrip.getUser();

            if (existingOrder.getCommuter() != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of(
                                "status", HttpStatus.BAD_REQUEST.value(),
                                "message", "Order already assigned"
                        )
                );
            }

            if (existingOrder.getOrderStatus().equals(OrderStatus.NOT_ACTIVE)) {
                existingOrder.setCommuter(existingCommuter);
                existingOrder.setTrip(existingTrip);
                orderRepository.save(existingOrder);

                return ResponseEntity.ok(
                        Map.of(
                                "status", HttpStatus.OK.value(),
                                "message", "Order assigned successfully, waiting for commuter to agree"
                        )
                );
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of(
                                "status", HttpStatus.BAD_REQUEST.value(),
                                "message", "Order status must be NOT_ACTIVE to assign it"
                        )
                );
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "status", HttpStatus.NOT_FOUND.value(),
                            "message", "Error: User ID or Order ID not found"
                    )
            );
        }
    }

}
