package com.graduationproject.controllers;

import com.graduationproject.DTOs.*;
import com.graduationproject.services.TripService;
import com.graduationproject.services.UserProfileService;
import com.graduationproject.services.UserService;
import com.graduationproject.services.impl.CommuterProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {

    private final UserProfileService userProfileService;
    private final UserService userService;
    private final TripService tripService;
    private final CommuterProfileService commuterProfileService;

    @PutMapping("update")
    public ResponseEntity<Object> updateUserProfile(@ModelAttribute UserProfileDTO userProfileDTO) {
        return userProfileService.updateUserProfile(userProfileDTO);
    }

    @GetMapping("get-normal-user-porfile")
    public ResponseEntity<Object> getNormalUserProfile(@RequestParam Integer id){
        return userProfileService.getNormalUserProfile(id);
    }

    @DeleteMapping("delete")
    public ResponseEntity<String> deleteById(@RequestParam Integer id, @RequestParam String phoneNumber){
        return userService.deleteById(id, phoneNumber);
    }

    @PostMapping("search-for-trip/{from}/{to}")
    public ResponseEntity<Object> searchForTrip(@PathVariable String from, @PathVariable String to){
        return tripService.searchForTrip(from,to);
    }

    @GetMapping("get-commuter-profile/{commuterId}")
    public ResponseEntity<Object> getCommuterProfile(@PathVariable Integer commuterId){
        return commuterProfileService.getFullCommuterProfile(commuterId);
    }

    @PutMapping("change-password")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        return userProfileService.changePassword(changePasswordDTO);
    }

}