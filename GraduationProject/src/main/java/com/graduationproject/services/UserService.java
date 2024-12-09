package com.graduationproject.services;

import com.graduationproject.entities.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {
        UserDetailsService userDetailsService();
        ResponseEntity<String> deleteById(Integer id, String phoneNumber);
        ResponseEntity<?> findUsersByRole(Role role);
        ResponseEntity<?> countUsersByRole(Role role);
}
