package com.graduationproject.mapper;

import com.graduationproject.DTOs.NormalProfileDTO;
import com.graduationproject.entities.Favorite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FavoriteMapper {

    NormalProfileDTO toNormalProfileDTO(Favorite favorite); // Maps Favorite entity to NormalProfileDTO
}