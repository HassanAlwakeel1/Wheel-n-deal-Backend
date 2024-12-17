package com.graduationproject.servicesImpl;

import com.graduationproject.DTOs.SearchOrderDTO;
import com.graduationproject.entities.Order;
import com.graduationproject.entities.User;
import com.graduationproject.mapper.OrderMapper;
import com.graduationproject.repositories.OrderRepository;
import com.graduationproject.repositories.UserRepository;
import com.graduationproject.services.OrderSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderSearchServiceImpl implements OrderSearchService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    public ResponseEntity<Object> searchForOrder(String from, String to) {
        if (from == null || to == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("status", HttpStatus.BAD_REQUEST.value(),
                            "message", "Origin and destination must not be null.")
            );
        }

        try {
            //List<SearchOrderDTO> searchOrderDTOS = new ArrayList<>();
            List<Order> orders = orderRepository.findByFromAndTo(from, to);

            if (orders == null || orders.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of("status", HttpStatus.NOT_FOUND.value(),
                                "message", "No orders found between " + from + " and " + to + ".")
                );
            }

            // Map Orders to SearchOrderDTO using OrderMapper
            List<SearchOrderDTO> searchOrderDTOs = orders.stream()
                    .map(orderMapper::orderToSearchOrderDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(
                    Map.of("status", HttpStatus.OK.value(),
                            "message", "Search results successfully retrieved.",
                            "data", searchOrderDTOs)
            );

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            "message", "An error occurred during the search.",
                            "data", ex.getMessage())
            );
        }
    }

    @Override
    public ResponseEntity<?> findAllByUserId(Integer userId) {
        if (userId == null || userId <= 0) {
            return ResponseEntity.badRequest().body(
                    Map.of("status", HttpStatus.BAD_REQUEST.value(),
                            "message", "Invalid user ID. User ID must be a positive integer.")
            );
        }

        List<Order> orders = orderRepository.findAllByUserId(userId);

        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", HttpStatus.NOT_FOUND.value(),
                            "message", "No orders found for the user.")
            );
        }

        return ResponseEntity.ok(
                Map.of("status", HttpStatus.OK.value(),
                        "message", "Orders found.",
                        "data", orders)
        );
    }

    @Override
    public ResponseEntity<Object> findNotActiveOrders(Integer userId) {
        if (userId == null || userId <= 0) {
            return ResponseEntity.badRequest().body(
                    Map.of("status", HttpStatus.BAD_REQUEST.value(),
                            "message", "Invalid userId provided.")
            );
        }

        List<Order> orders = orderRepository.findNotActiveOrders(userId);

        if (orders == null || orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", HttpStatus.NOT_FOUND.value(),
                            "message", "No not active orders found for the user.")
            );
        }

        return ResponseEntity.ok(
                Map.of("status", HttpStatus.OK.value(),
                        "message", "Not active orders found.",
                        "data", orders)
        );
    }


    @Override
    public ResponseEntity<?> findById(Integer orderId) {
        if (orderId == null || orderId <= 0) {
            return ResponseEntity.badRequest().body(
                    Map.of("status", HttpStatus.BAD_REQUEST.value(),
                            "message", "Invalid order ID. Order ID must be a positive integer.")
            );
        }

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("status", HttpStatus.NOT_FOUND.value(),
                            "message", "Order not found.")
            );
        }

        Order order = optionalOrder.get();
        return ResponseEntity.ok(
                Map.of("status", HttpStatus.OK.value(),
                        "message", "Order found.",
                        "data", order)
        );
    }
    @Override
    public ResponseEntity<?> findCommuterOrders(Integer commuterId) {
        if (commuterId == null || commuterId <= 0) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", HttpStatus.BAD_REQUEST.value(),
                            "message", "Invalid commuter ID"
                    )
            );
        }

        Optional<User> optionalCommuter = userRepository.findById(commuterId);
        if (!optionalCommuter.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "status", HttpStatus.NOT_FOUND.value(),
                            "message", "Commuter ID not found"
                    )
            );
        }

        List<Order> orderList = orderRepository.findCommuterOrders(commuterId);
        if (orderList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    Map.of(
                            "status", HttpStatus.NO_CONTENT.value(),
                            "message", "No orders found for this commuter"
                    )
            );
        }

        return ResponseEntity.ok(
                Map.of(
                        "status", HttpStatus.OK.value(),
                        "message", "Orders retrieved successfully",
                        "data", orderList
                )
        );
    }
}
