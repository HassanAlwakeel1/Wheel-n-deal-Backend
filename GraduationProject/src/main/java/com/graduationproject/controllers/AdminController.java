package com.graduationproject.controllers;

import com.graduationproject.DTOs.UserDTO;
import com.graduationproject.enums.Role;
import com.graduationproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin")
public class AdminController {

    private final UserService userService;

    @GetMapping("users")
    public ResponseEntity<List<UserDTO>> findUsersByRole(@RequestParam Role role) {
        if (role == null) {
            return ResponseEntity.badRequest().body(null);
        }
        try {
            List<UserDTO> users = userService.findUsersByRole(role);
            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("users/count")
    public ResponseEntity<Object> countUsersByRole(@RequestParam Role role) {
        return userService.countUsersByRole(role);
    }

}