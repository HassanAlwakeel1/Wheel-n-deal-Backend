package com.graduationproject.mapper;

import com.graduationproject.DTOs.CommuterProfileDTO;
import com.graduationproject.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommuterProfileMapper {
    CommuterProfileDTO toDTO(User user);
}