package com.graduationproject.mapper;

import com.graduationproject.DTOs.NormalProfileDTO;
import com.graduationproject.entities.Favorite;

public interface FavoriteMapper {
    NormalProfileDTO toNormalProfileDTO(Favorite favorite);
}
