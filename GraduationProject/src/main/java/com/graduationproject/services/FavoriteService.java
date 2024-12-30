package com.graduationproject.services;

import org.springframework.http.ResponseEntity;

public interface FavoriteService {
    ResponseEntity<?> manageFavoriteUser(Integer userId, Integer favoriteUserID);
    ResponseEntity<?> getUserFavorites(Integer userId);

}