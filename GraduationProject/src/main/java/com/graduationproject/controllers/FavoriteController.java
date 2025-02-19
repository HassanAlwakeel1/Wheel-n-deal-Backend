package com.graduationproject.controllers;

import com.graduationproject.services.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("manageFavorites/{userId}/{favoriteUserId}")
    public ResponseEntity<?> manageFavoriteUser(@PathVariable int userId, @PathVariable int favoriteUserId) {
        return favoriteService.manageFavoriteUser(userId, favoriteUserId);
    }

    @PostMapping("getUserFavorites/{userId}")
    public ResponseEntity<?> getUserFavorites(@PathVariable Integer userId){
        return favoriteService.getUserFavorites(userId);
    }

}