package com.graduationproject.servicesImpl;

import com.graduationproject.DTOs.CommuterProfileDTO;
import com.graduationproject.DTOs.ProfileReviewsDTO;
import com.graduationproject.DTOs.ProfileTripDetailsDTO;
import com.graduationproject.entities.Review;
import com.graduationproject.entities.Role;
import com.graduationproject.entities.Trip;
import com.graduationproject.entities.User;
import com.graduationproject.mapper.CommuterProfileMapper;
import com.graduationproject.mapper.ReviewMapper;
import com.graduationproject.mapper.TripMapper;
import com.graduationproject.repositories.ReviewRepository;
import com.graduationproject.repositories.TripRepository;
import com.graduationproject.repositories.UserRepository;
import com.graduationproject.services.CommuterProfileService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@Service
@RequiredArgsConstructor
public class CommuterProfileServiceImpl implements CommuterProfileService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final ReviewMapper reviewMapper;
    private final CommuterProfileMapper commuterProfileMapper;

    public ResponseEntity<Object> getFullCommuterProfile(Integer commuterId) {
        if (commuterId == null) {
            return new ResponseEntity<>(
                    Map.of("status", HttpStatus.BAD_REQUEST.value(), "message", "Commuter ID must be provided."),
                    HttpStatus.BAD_REQUEST
            );
        }

        Optional<User> optionalUser = userRepository.findById(commuterId);
        if (optionalUser.isEmpty()) {
            return new ResponseEntity<>(
                    Map.of("status", HttpStatus.NOT_FOUND.value(), "message", "Commuter not found for ID: " + commuterId),
                    HttpStatus.NOT_FOUND
            );
        }

        User user = optionalUser.get();

        if (user.getRole() != Role.COMMUTER) {
            return new ResponseEntity<>(
                    Map.of("status", HttpStatus.FORBIDDEN.value(), "message", "Unauthorized role: User is not a commuter"),
                    HttpStatus.FORBIDDEN
            );
        }

        CommuterProfileDTO commuterProfileDTO = commuterProfileMapper.toDTO(user);
        commuterProfileDTO.setTotalRate(calculateCommuterTotalRate(user.getId()));
        commuterProfileDTO.setProfileTripDetailsDTOs(profileTripDetailsDTOList(user.getId()));
        commuterProfileDTO.setProfileReviewsDTOS(profileReviewsDTOS(user.getId()));

        return new ResponseEntity<>(
                Map.of("status", HttpStatus.OK.value(), "message", "Commuter profile retrieved successfully", "data", commuterProfileDTO),
                HttpStatus.OK
        );
    }

    private double calculateCommuterTotalRate(int commuterId){
        double totalRate = 0;
        double rateSum = 0;
        Optional<User> optionalUser = userRepository.findById(commuterId);
        if(optionalUser.isPresent()){
            List<Review> receivedReviews = optionalUser.get().getReceivedReviews();
            if (receivedReviews.isEmpty()) {
                return 0; // Return default value if no reviews
            }
            for (Review review : receivedReviews){
                rateSum += (double) review.getRate();
            }
            totalRate = rateSum/receivedReviews.size();
        }
        return totalRate;
    }

    private List<ProfileTripDetailsDTO> profileTripDetailsDTOList(int commuterId){
        List<ProfileTripDetailsDTO> tripDetailsDTOsList = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(commuterId);
        if(optionalUser.isPresent() && optionalUser.get().getRole() == Role.COMMUTER){
            User user = optionalUser.get();
            List<Trip> trips = user.getUserTrips();
            for(Trip trip : trips){
                ProfileTripDetailsDTO profileTripDetailsDTO = tripMapper.toDTO(trip); // Use the TripMapper
                tripDetailsDTOsList.add(profileTripDetailsDTO);
            }
        }
        return tripDetailsDTOsList;
    }

    private List<ProfileReviewsDTO> profileReviewsDTOS(int commuterId){
        List<ProfileReviewsDTO> profileReviewsDTOS = new ArrayList<>();
        Optional<User> optionalUser = userRepository.findById(commuterId);
        if(optionalUser.isPresent() && optionalUser.get().getRole() == Role.COMMUTER){
            User user = optionalUser.get();
            List<Review> receivedReviewsList = user.getReceivedReviews();
            for(Review review : receivedReviewsList){
                ProfileReviewsDTO profileReviewsDTO = reviewMapper.toProfileReviewsDTO(review); // Use the ReviewMapper
                profileReviewsDTOS.add(profileReviewsDTO);
            }
        }
        return profileReviewsDTOS;
    }
}