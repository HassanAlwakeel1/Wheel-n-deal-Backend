package com.graduationproject.services;

import org.springframework.http.ResponseEntity;

public interface OrderSearchService {
    ResponseEntity<Object> searchForOrder(String from, String to);
    ResponseEntity<?> findAllByUserId(Integer userId);
    ResponseEntity<Object> findNotActiveOrders(Integer userId);
    ResponseEntity<?> findById(Integer orderId);
    ResponseEntity<?> findCommuterOrders(Integer commuterId);

}
