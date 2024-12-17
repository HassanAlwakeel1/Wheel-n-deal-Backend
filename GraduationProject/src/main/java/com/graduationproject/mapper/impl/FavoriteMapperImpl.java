package com.graduationproject.mapper.impl;

import com.graduationproject.DTOs.NormalProfileDTO;
import com.graduationproject.entities.Favorite;
import com.graduationproject.mapper.FavoriteMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class FavoriteMapperImpl implements FavoriteMapper {
    private ModelMapper modelMapper;

    public FavoriteMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public NormalProfileDTO toNormalProfileDTO(Favorite favorite) {
        return modelMapper.map(favorite.getFavoriteUser(), NormalProfileDTO.class);
    }
}
