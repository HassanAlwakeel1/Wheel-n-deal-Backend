package com.graduationproject.services;

import com.graduationproject.DTOs.*;
import com.graduationproject.entities.Order;
import org.springframework.http.ResponseEntity;

public interface OrderService {
    ResponseEntity<Object> createOrUpdateOrder(OrderDTO orderDTO);
    ResponseEntity<Object> updateOrderFromDTO(Order order, OrderDTO orderDTO);
    ResponseEntity<Object> updateSearchOrderDTOFromOrder(SearchOrderDTO searchOrderDTO, Order order);
    ResponseEntity<Object> searchForOrder(String from, String to);
    ResponseEntity<?> findAllByUserId(Integer userId);

    ResponseEntity<Object> findNotActiveOrders(Integer userId);
    ResponseEntity<?> assignExistingOrder(Integer orderId, Integer tripId);
    ResponseEntity<Object> createOrderAndAssignIt(OrderDTO orderDTO, Integer tripId);

    ResponseEntity<?> cancelOrder(Integer orderId, Integer cancelerId);

    ResponseEntity<?> findCommuterOrders(Integer commuterId);
    ResponseEntity<?> negotiate(NegotiationDTO negotiationDTO);

    ResponseEntity<?> pickOrder(OrderPickDTO orderPickDTO);
    ResponseEntity<?> getOrderApplicants(Integer orderId);
    ResponseEntity<?> confirmPickingUp(ConfirmPickingUpDTO confirmPickingUpDTO);
    ResponseEntity<?> findById(Integer orderId);
    ResponseEntity<?> declineOrder(Integer orderId);
    ResponseEntity<?> confirmOrder(Integer orderId);
}
