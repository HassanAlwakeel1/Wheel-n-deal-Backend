package com.graduationproject.services;

import com.graduationproject.DTOs.ChangePasswordDTO;
import com.graduationproject.DTOs.UserProfileDTO;
import org.springframework.http.ResponseEntity;

public interface UserProfileService {
    ResponseEntity<Object> updateUserProfile(UserProfileDTO userProfileDTO);
    ResponseEntity<Object> getNormalUserProfile(Integer id);
    ResponseEntity<Object> changePassword(ChangePasswordDTO changePasswordDTO);
}