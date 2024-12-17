package com.graduationproject.mapper;

import com.graduationproject.DTOs.ProfileTripDetailsDTO;
import com.graduationproject.DTOs.TripDTO;
import com.graduationproject.entities.Trip;

public interface TripMapper {
    TripDTO mapToDTO(Trip trip); // Maps Trip entity to TripDTO
    Trip mapToEntity(TripDTO tripDTO); // Maps TripDTO to Trip entity

    void updateEntityFromDTO(TripDTO tripDTO, Trip trip); // Updates existing Trip entity with data from TripDTO
    ProfileTripDetailsDTO toDTO(Trip trip);
}
