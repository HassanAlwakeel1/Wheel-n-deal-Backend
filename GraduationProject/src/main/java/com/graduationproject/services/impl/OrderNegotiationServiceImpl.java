package com.graduationproject.services.impl;

import com.graduationproject.DTOs.NegotiationDTO;
import com.graduationproject.entities.Order;
import com.graduationproject.repositories.OrderRepository;
import com.graduationproject.services.OrderNegotiationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderNegotiationServiceImpl implements OrderNegotiationService {

    private final OrderRepository orderRepository;

    /**
     * Negotiate a new price for an order.
     * @param negotiationDTO the DTO containing the negotiation details.
     * @return a ResponseEntity with the result of the negotiation.
     */
    @Override
    public ResponseEntity<?> negotiate(NegotiationDTO negotiationDTO) {
        // Validate input DTO
        if (negotiationDTO == null || negotiationDTO.getOrderId() == null || negotiationDTO.getNewPrice() == null) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", HttpStatus.BAD_REQUEST.value(),
                            "message", "Invalid negotiation details. Order ID and new price are required."
                    )
            );
        }

        // Validate the price
        if (negotiationDTO.getNewPrice() <= 0) {
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "status", HttpStatus.BAD_REQUEST.value(),
                            "message", "Invalid price. Price must be greater than zero."
                    )
            );
        }

        // Check if the order exists
        Optional<Order> optionalOrder = orderRepository.findById(negotiationDTO.getOrderId());
        if (!optionalOrder.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of(
                            "status", HttpStatus.NOT_FOUND.value(),
                            "message", "Order not found"
                    )
            );
        }

        // Update the order's negotiation price
        Order existingOrder = optionalOrder.get();
        existingOrder.setNegotiationPrice(negotiationDTO.getNewPrice());
        orderRepository.save(existingOrder);

        // Respond with success
        return ResponseEntity.ok(
                Map.of(
                        "status", HttpStatus.OK.value(),
                        "message", "Negotiation successful. We will inform the customer about the new price and await their response."
                )
        );
    }
}
