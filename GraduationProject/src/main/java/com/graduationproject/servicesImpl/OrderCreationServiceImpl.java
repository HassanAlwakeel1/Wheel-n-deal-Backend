package com.graduationproject.servicesImpl;

import com.graduationproject.DTOs.OrderDTO;
import com.graduationproject.entities.Order;
import com.graduationproject.entities.OrderStatus;
import com.graduationproject.entities.Trip;
import com.graduationproject.entities.User;
import com.graduationproject.repositories.OrderRepository;
import com.graduationproject.repositories.TripRepository;
import com.graduationproject.repositories.UserRepository;
import com.graduationproject.services.OrderCreationService;
import com.graduationproject.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderCreationServiceImpl implements OrderCreationService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;

    @Transactional
    @Override
    public ResponseEntity<Object> createOrUpdateOrder(OrderDTO orderDTO) {
        if (orderDTO == null) {
            return new ResponseEntity<>(Map.of(
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "message", "OrderDTO cannot be null."
            ), HttpStatus.BAD_REQUEST);
        }

        if (orderDTO.getId() != null) {
            return updateExistingOrder(orderDTO);
        } else {
            return createNewOrder(orderDTO);
        }
    }

    // Update existing order if order ID is provided
    private ResponseEntity<Object> updateExistingOrder(OrderDTO orderDTO) {
        Optional<Order> optionalOrder = orderRepository.findById(orderDTO.getId());

        if (optionalOrder.isPresent()) {
            Order existingOrder = optionalOrder.get();

            // Check if the order status is NOT_ACTIVE (can only update non-active orders)
            if (existingOrder.getOrderStatus().equals(OrderStatus.NOT_ACTIVE)) {
                updateOrderFromDTO(existingOrder, orderDTO);
                orderRepository.save(existingOrder);

                return new ResponseEntity<>(Map.of(
                        "status", HttpStatus.OK.value(),
                        "message", "Order updated successfully"
                ), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Map.of(
                        "status", HttpStatus.FORBIDDEN.value(),
                        "message", "You are allowed to update only non-active orders"
                ), HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity<>(Map.of(
                    "status", HttpStatus.NOT_FOUND.value(),
                    "message", "Order not found with ID: " + orderDTO.getId()
            ), HttpStatus.NOT_FOUND);
        }
    }

    // Helper method to create a new order
    private ResponseEntity<Object> createNewOrder(OrderDTO orderDTO) {
        Optional<User> optionalUser = userRepository.findById(orderDTO.getUserId());

        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>(Map.of(
                    "status", HttpStatus.NOT_FOUND.value(),
                    "message", "User not found with ID: " + orderDTO.getUserId()
            ), HttpStatus.NOT_FOUND);
        }

        // Create a new order if the user exists
        Order order = new Order();
        updateOrderFromDTO(order, orderDTO);

        User user = optionalUser.get();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.NOT_ACTIVE);

        Order savedOrder = orderRepository.save(order);

        return new ResponseEntity<>(Map.of(
                "status", HttpStatus.CREATED.value(),
                "message", "Order created successfully",
                "orderId", savedOrder.getId()
        ), HttpStatus.CREATED);
    }

    // Helper method to update order from DTO
    private ResponseEntity<Object> updateOrderFromDTO(Order order, OrderDTO orderDTO) {
        if (order == null) {
            return new ResponseEntity<>(Map.of(
                    "status", HttpStatus.NOT_FOUND.value(),
                    "message", "Order not found."
            ), HttpStatus.NOT_FOUND);
        }

        try {
            if (orderDTO == null) {
                return new ResponseEntity<>(Map.of(
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "message", "OrderDTO is null."
                ), HttpStatus.BAD_REQUEST);
            }

            if (orderDTO.getOrderName() == null || orderDTO.getFrom() == null || orderDTO.getTo() == null) {
                return new ResponseEntity<>(Map.of(
                        "status", HttpStatus.BAD_REQUEST.value(),
                        "message", "Missing required fields in OrderDTO."
                ), HttpStatus.BAD_REQUEST);
            }

            order.setOrderName(orderDTO.getOrderName());
            order.setCountOfOrders(orderDTO.getCountOfOrders());
            order.setWeight(orderDTO.getWeight());
            order.setBreakable(orderDTO.isBreakable());
            order.setExpiryDate(orderDTO.getExpiryDate());
            order.setExpectedPrice(orderDTO.getExpectedPrice());
            order.setOrderPhotoUrl(Utils.storePhotoAndGetUrl(orderDTO.getOrderPhoto()));
            order.setFrom(orderDTO.getFrom());
            order.setTo(orderDTO.getTo());
            order.setSenderName(orderDTO.getSenderName());
            order.setSenderPhoneNumber(orderDTO.getSenderPhoneNumber());
            order.setReceiverName(orderDTO.getReceiverName());
            order.setReceiverPhoneNumber(orderDTO.getReceiverPhoneNumber());

            return new ResponseEntity<>(Map.of(
                    "status", HttpStatus.OK.value(),
                    "message", "Order updated successfully.",
                    "data", order
            ), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(Map.of(
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "message", "An error occurred while updating the order.",
                    "data", e.getMessage()
            ), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a new order and assign it to a trip
    @Transactional
    @Override
    public ResponseEntity<Object> createOrderAndAssignIt(OrderDTO orderDTO, Integer tripId) {
        Optional<User> optionalUser = userRepository.findById(orderDTO.getUserId());
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(Map.of(
                    "status", HttpStatus.NOT_FOUND.value(),
                    "message", "User ID not found"
            ), HttpStatus.NOT_FOUND);
        }

        Optional<Trip> optionalTrip = tripRepository.findById(tripId);
        if (optionalTrip.isEmpty()) {
            return new ResponseEntity<>(Map.of(
                    "status", HttpStatus.NOT_FOUND.value(),
                    "message", "Trip ID not found"
            ), HttpStatus.NOT_FOUND);
        }

        Order order = new Order();
        updateOrderFromDTO(order, orderDTO);

        User user = optionalUser.get();
        order.setUser(user);

        Trip trip = optionalTrip.get();
        order.setTrip(trip);
        order.setOrderStatus(OrderStatus.NOT_ACTIVE);

        orderRepository.save(order);

        return new ResponseEntity<>(Map.of(
                "status", HttpStatus.OK.value(),
                "message", "Order saved and assigned successfully, waiting for commuter's agreement"
        ), HttpStatus.OK);
    }
}
