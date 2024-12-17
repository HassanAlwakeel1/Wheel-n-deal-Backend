package com.graduationproject.mapper.impl;

import com.graduationproject.DTOs.CommuterProfileDTO;
import com.graduationproject.entities.User;
import com.graduationproject.mapper.CommuterProfileMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommuterProfileMapperImpl implements CommuterProfileMapper {
    private final ModelMapper modelMapper;

    public CommuterProfileMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public CommuterProfileDTO toDTO(User user) {
        CommuterProfileDTO commuterProfileDTO = modelMapper.map(user, CommuterProfileDTO.class);
        return commuterProfileDTO;
    }
}
