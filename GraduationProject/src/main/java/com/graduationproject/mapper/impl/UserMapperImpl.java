package com.graduationproject.mapper.impl;

import com.graduationproject.DTOs.CommuterProfileDTO;
import com.graduationproject.DTOs.NormalProfileDTO;
import com.graduationproject.DTOs.UserProfileDTO;
import com.graduationproject.entities.User;
import com.graduationproject.mapper.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserMapperImpl implements UserMapper {
    private ModelMapper modelMapper;

    public UserMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public User toEntity(UserProfileDTO userProfileDTO) {
        return modelMapper.map(userProfileDTO, User.class);
    }

    @Override
    public NormalProfileDTO toDTO(User user) {
        return modelMapper.map(user, NormalProfileDTO.class);
    }

}
