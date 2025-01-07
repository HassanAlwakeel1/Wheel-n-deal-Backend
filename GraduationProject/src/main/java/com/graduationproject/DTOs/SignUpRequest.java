package com.graduationproject.DTOs;

import com.graduationproject.enums.Role;
import lombok.Data;

@Data
public class SignUpRequest {
    private String phone;
    private String username;
    private String password;
    private String confirmPassword;
    private Role role;
}