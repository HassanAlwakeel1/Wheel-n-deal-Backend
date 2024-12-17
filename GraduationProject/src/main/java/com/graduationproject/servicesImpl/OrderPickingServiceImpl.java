package com.graduationproject.servicesImpl;

import com.graduationproject.DTOs.ApplicantDTO;
import com.graduationproject.DTOs.ConfirmPickingUpDTO;
import com.graduationproject.DTOs.OrderPickDTO;
import com.graduationproject.entities.*;
import com.graduationproject.mapper.OrderMapper;
import com.graduationproject.repositories.OrderApplicantsRepository;
import com.graduationproject.repositories.OrderRepository;
import com.graduationproject.repositories.PromoCodeRepository;
import com.graduationproject.repositories.UserRepository;
import com.graduationproject.services.OrderPickingService;
import com.graduationproject.services.PromocodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderPickingServiceImpl implements OrderPickingService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderApplicantsRepository orderApplicantsRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final PromocodeService promocodeService;
    private final OrderMapper orderMapper;

    @Override
    public ResponseEntity<?> pickOrder(OrderPickDTO orderPickDTO) {
        // Validate order ID and commuter ID
        ResponseEntity<?> validationResponse = validateOrderAndCommuter(orderPickDTO.getOrderId(), orderPickDTO.getCommuterId());
        if (validationResponse != null) return validationResponse;

        // Check if the price is valid
        if (orderPickDTO.getApllicantPrice() <= 0) {
            return badRequestResponse("Price must be greater than zero.");
        }

        // Get order and commuter
        Order order = orderRepository.findById(orderPickDTO.getOrderId()).get();
        User commuter = userRepository.findById(orderPickDTO.getCommuterId()).get();

        // Check if the commuter has already applied for this order
        if (orderApplicantsRepository.findByOrderAndCommuter(order, commuter) != null) {
            return badRequestResponse("You have already submitted an offer for this order.");
        }

        // Create a new application for the order
        OrderApplicants orderApplicant = orderMapper.mapToOrderApplicants(orderPickDTO, order, commuter);

        return successResponse("Order picked successfully. We will inform you when the user accepts your offer.");
    }

    @Override
    public ResponseEntity<?> confirmPickingUp(ConfirmPickingUpDTO confirmPickingUpDTO) {
        // Validate order ID and commuter ID
        ResponseEntity<?> validationResponse = validateOrderAndCommuter(confirmPickingUpDTO.getOrderId(), confirmPickingUpDTO.getCommuterId());
        if (validationResponse != null) return validationResponse;

        Order order = orderRepository.findById(confirmPickingUpDTO.getOrderId()).get();
        User commuter = userRepository.findById(confirmPickingUpDTO.getCommuterId()).get();
        User user = order.getUser();

        PaymentMethod paymentMethod = confirmPickingUpDTO.getPaymentMethod();
        String promoCode = confirmPickingUpDTO.getPromoCode();
        double actualPrice = confirmPickingUpDTO.getPrice();

        if (paymentMethod == PaymentMethod.WALLET) {
            return handleWalletPayment(actualPrice, promoCode, user, commuter, order);
        } else if (paymentMethod == PaymentMethod.CASH) {
            return handleCashPayment(order, commuter);
        }

        return badRequestResponse("Invalid payment method.");
    }

    @Override
    public ResponseEntity<?> getOrderApplicants(Integer orderId) {
        if (orderId == null || orderId <= 0) {
            return badRequestResponse("Invalid order ID.");
        }

        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) {
            return notFoundResponse("Order not found.");
        }

        List<OrderApplicants> applicants = orderApplicantsRepository.findByOrder(optionalOrder.get());
        if (applicants.isEmpty()) {
            return notFoundResponse("No applicants found for this order.");
        }

        List<ApplicantDTO> applicantData = new ArrayList<>();
        for (OrderApplicants applicant : applicants) {
            ApplicantDTO applicantDTO = orderMapper.mapToApplicantDTO(applicant);
            applicantData.add(applicantDTO);
        }

        return successResponse("Applicants found for the order.", applicantData);
    }

    // Helper methods
    private ResponseEntity<?> validateOrderAndCommuter(Integer orderId, Integer commuterId) {
        if (orderId == null || orderId <= 0) {
            return badRequestResponse("Invalid order ID.");
        }

        if (commuterId == null || commuterId <= 0) {
            return badRequestResponse("Invalid commuter ID.");
        }

        if (orderRepository.findById(orderId).isEmpty()) {
            return notFoundResponse("Order not found.");
        }

        if (userRepository.findById(commuterId).isEmpty()) {
            return notFoundResponse("Commuter not found.");
        }

        return null;
    }

    private ResponseEntity<?> handleWalletPayment(double actualPrice, String promoCode, User user, User commuter, Order order) {
        double appBenefits = actualPrice * 0.07;
        double commuterEarnings = actualPrice - appBenefits;

        if (promoCode == null) {
            updateWalletBalances(user, commuterEarnings);
            assignOrderToCommuter(order, commuter);
            return successResponse("Order assigned successfully. Proceed to pick it up.");
        }

        if (!promocodeService.checkPromoCode(promoCode)) {
            return badRequestResponse("Invalid or inactive promo code.");
        }

        PromoCode promo = promoCodeRepository.findByPromoCode(promoCode);
        if (promo == null || !promo.getUser().equals(user) || promo.getPromocodeStatus() != PromocodeStatus.ACTIVE) {
            return badRequestResponse("Promo code does not belong to this user or is not active.");
        }

        actualPrice -= promo.getDiscountAmount();
        updateWalletBalances(user, commuterEarnings);
        promo.setPromocodeStatus(PromocodeStatus.NON_ACTIVE);
        promoCodeRepository.save(promo);

        assignOrderToCommuter(order, commuter);
        return successResponse("Order assigned successfully. Proceed to pick it up.");
    }

    private void updateWalletBalances(User user, double actualPrice) {
        user.setAmount(user.getAmount() - (long) actualPrice);
        User admin = userRepository.findById(1).orElse(null);
        if (admin != null) {
            admin.setAmount(admin.getAmount() + (long) actualPrice);
            userRepository.save(admin);
        }
        userRepository.save(user);
    }

    private ResponseEntity<?> handleCashPayment(Order order, User commuter) {
        assignOrderToCommuter(order, commuter);
        return successResponse("Order assigned successfully. Proceed to pick it up.");
    }

    private void assignOrderToCommuter(Order order, User commuter) {
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCommuter(commuter);
        orderRepository.save(order);
    }

    private ResponseEntity<?> badRequestResponse(String message) {
        return ResponseEntity.badRequest().body(Map.of("status", HttpStatus.BAD_REQUEST.value(), "message", message));
    }

    private ResponseEntity<?> notFoundResponse(String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", HttpStatus.NOT_FOUND.value(), "message", message));
    }

    private ResponseEntity<?> successResponse(String message) {
        return ResponseEntity.ok(Map.of("status", HttpStatus.OK.value(), "message", message));
    }

    private ResponseEntity<?> successResponse(String message, Object data) {
        return ResponseEntity.ok(Map.of("status", HttpStatus.OK.value(), "message", message, "data", data));
    }
}
