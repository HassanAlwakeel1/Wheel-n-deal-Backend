package com.graduationproject.servicesImpl;

import com.graduationproject.DTOs.OrderDTO;
import com.graduationproject.entities.*;
import com.graduationproject.repositories.OrderRepository;
import com.graduationproject.repositories.UserRepository;
import com.graduationproject.services.OrderManagementService;
import com.graduationproject.services.PromocodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderManagementServiceImpl implements OrderManagementService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final PromocodeService promocodeService;

    @Override
    public ResponseEntity<?> cancelOrder(Integer orderId, Integer cancelerId) {
        // Step 1: Validate inputs
        if (isInvalidId(orderId) || isInvalidId(cancelerId)) {
            return badRequest("Invalid order ID or canceler ID.");
        }

        // Step 2: Find order and canceler
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return notFound("Order not found");
        }

        Optional<User> optionalCanceller = userRepository.findById(cancelerId);
        if (optionalCanceller.isEmpty()) {
            return notFound("Canceller not found");
        }

        Order order = optionalOrder.get();
        User canceller = optionalCanceller.get();
        Role cancellerRole = determineCancellerRole(order, canceller);
        if (cancellerRole == null) {
            return notFound("Canceller does not have permission.");
        }

        // Step 3: Process cancellation based on order status
        return processOrderCancellation(order, cancellerRole);
    }

    private boolean isInvalidId(Integer id) {
        return id == null || id <= 0;
    }

    private ResponseEntity<?> badRequest(String message) {
        return ResponseEntity.badRequest().body(Map.of("status", HttpStatus.BAD_REQUEST.value(), "message", message));
    }

    private ResponseEntity<?> notFound(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", HttpStatus.NOT_FOUND.value(), "message", message));
    }

    private Role determineCancellerRole(Order order, User canceller) {
        if (canceller.equals(order.getUser())) {
            return Role.USER;
        } else if (canceller.equals(order.getCommuter())) {
            return Role.COMMUTER;
        }
        return null;
    }

    private ResponseEntity<?> processOrderCancellation(Order order, Role cancellerRole) {
        OrderStatus orderStatus = order.getOrderStatus();
        Long orderPrice = (long) order.getExpectedPrice();
        User orderOwner = order.getUser();
        User commuter = order.getCommuter();
        User admin = getAdminUser();

        if (admin == null) {
            return internalServerError("Admin user not found");
        }

        // Adjust amounts based on the order status and canceller role
        adjustOrderAmounts(order, orderStatus, cancellerRole, orderPrice, orderOwner, commuter, admin);

        // Save updated entities and return success response
        orderRepository.save(order);
        return ResponseEntity.ok(Map.of("status", HttpStatus.OK.value(), "message", "Order canceled successfully"));
    }

    private User getAdminUser() {
        return userRepository.findById(1).orElse(null);
    }

    private ResponseEntity<?> internalServerError(String message) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("status", HttpStatus.INTERNAL_SERVER_ERROR.value(), "message", message));
    }

    private void adjustOrderAmounts(Order order, OrderStatus orderStatus, Role cancellerRole, Long orderPrice, User orderOwner, User commuter, User admin) {
        Long orderOwnerAmount = orderOwner.getAmount();
        Long commuterAmount = (commuter != null) ? commuter.getAmount() : 0L;
        Long adminAmount = admin.getAmount();

        if (orderStatus == OrderStatus.PENDING) {
            handlePendingStatus(cancellerRole, orderPrice, orderOwnerAmount, commuterAmount);
        } else if (orderStatus == OrderStatus.IN_PROGRESS) {
            handleInProgressStatus(cancellerRole, orderPrice, orderOwnerAmount, commuterAmount, commuter, order);
        } else if (orderStatus == OrderStatus.CONFIRMED) {
            handleConfirmedStatus(cancellerRole, orderPrice, orderOwnerAmount, commuterAmount);
        }

        // Update amounts and save entities
        orderOwner.setAmount(orderOwnerAmount);
        if (commuter != null) commuter.setAmount(commuterAmount);
        admin.setAmount(adminAmount);
        order.setOrderStatus(OrderStatus.FAILED);
    }

    private void handlePendingStatus(Role cancellerRole, Long orderPrice, Long orderOwnerAmount, Long commuterAmount) {
        if (cancellerRole == Role.USER) {
            orderOwnerAmount -= (orderPrice * 10 / 100);
            commuterAmount += (orderPrice * 10 / 100);
        } else if (cancellerRole == Role.COMMUTER) {
            commuterAmount -= (orderPrice * 10 / 100);
            orderOwnerAmount += (orderPrice * 10 / 100);
        }
    }

    private void handleInProgressStatus(Role cancellerRole, Long orderPrice, Long orderOwnerAmount, Long commuterAmount, User commuter, Order order) {
        if (commuter != null) {
            commuter.setCancelDelivers(commuter.getCancelDelivers() + 1);
        }

        if (cancellerRole == Role.USER) {
            orderOwnerAmount -= (orderPrice * 2);
            User admin = getAdminUser();
            admin.setAmount(admin.getAmount() + (orderPrice * 2));
        } else if (cancellerRole == Role.COMMUTER) {
            commuterAmount -= (orderPrice * 10 / 100);
            promocodeService.generatePromoCode(order.getOrderId());
        }
    }

    private void handleConfirmedStatus(Role cancellerRole, Long orderPrice, Long orderOwnerAmount, Long commuterAmount) {
        if (cancellerRole == Role.USER) {
            orderOwnerAmount -= (orderPrice * 10 / 100);
            commuterAmount += (orderPrice * 10 / 100);
        } else if (cancellerRole == Role.COMMUTER) {
            commuterAmount -= (orderPrice * 10 / 100);
            orderOwnerAmount += (orderPrice * 10 / 100);
        }
    }

    @Override
    public ResponseEntity<?> declineOrder(Integer orderId) {
        // Validate order ID
        if (isInvalidId(orderId)) {
            return badRequest("Invalid order ID.");
        }

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return notFound("Order not found.");
        }

        Order order = optionalOrder.get();
        if (order.getCommuter() == null) {
            return conflict("Order has no assigned commuter to decline.");
        }

        // Decline the order
        order.setCommuter(null);
        orderRepository.save(order);
        return ResponseEntity.ok(Map.of("status", HttpStatus.OK.value(), "message", "Order declined successfully."));
    }

    private ResponseEntity<?> conflict(String message) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("status", HttpStatus.CONFLICT.value(), "message", message));
    }

    @Override
    public ResponseEntity<?> confirmOrder(Integer orderId) {
        // Validate order ID
        if (isInvalidId(orderId)) {
            return badRequest("Invalid order ID.");
        }

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return notFound("Order not found.");
        }

        Order order = optionalOrder.get();

        // Ensure the order is not already confirmed or completed
        if (order.getOrderStatus() == OrderStatus.PENDING || order.getOrderStatus() == OrderStatus.CONFIRMED) {
            return conflict("Order is already confirmed or completed.");
        }

        // Confirm the order
        order.setOrderStatus(OrderStatus.PENDING);
        orderRepository.save(order);
        return ResponseEntity.ok(Map.of("status", HttpStatus.OK.value(), "message", "Order confirmed successfully. Go and pick it up."));
    }
}
