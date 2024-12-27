package com.graduationproject.services;

import com.graduationproject.DTOs.UserDTO;
import com.graduationproject.entities.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserService {
        UserDetailsService userDetailsService();
        ResponseEntity<String> deleteById(Integer id, String phoneNumber);
        List<UserDTO> findUsersByRole(Role role);
        ResponseEntity<Object> countUsersByRole(Role role);
}
