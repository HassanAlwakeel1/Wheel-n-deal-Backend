package com.graduationproject.mapper;

import com.graduationproject.DTOs.NormalProfileDTO;
import com.graduationproject.DTOs.UserProfileDTO;
import com.graduationproject.entities.User;

public interface UserMapper {
    User toEntity(UserProfileDTO dto);
    NormalProfileDTO toDTO(User user);
}
