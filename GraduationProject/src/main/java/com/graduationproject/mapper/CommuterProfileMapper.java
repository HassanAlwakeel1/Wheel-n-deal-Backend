package com.graduationproject.mapper;

import com.graduationproject.DTOs.CommuterProfileDTO;
import com.graduationproject.entities.User;

public interface CommuterProfileMapper {
    CommuterProfileDTO toDTO(User user);
}
