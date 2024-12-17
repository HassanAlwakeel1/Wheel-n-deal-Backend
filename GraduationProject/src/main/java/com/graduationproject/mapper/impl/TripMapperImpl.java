package com.graduationproject.mapper.impl;

import com.graduationproject.DTOs.ProfileTripDetailsDTO;
import com.graduationproject.DTOs.TripDTO;
import com.graduationproject.entities.Trip;
import com.graduationproject.mapper.TripMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TripMapperImpl implements TripMapper {

    private final ModelMapper modelMapper;

    public TripMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TripDTO mapToDTO(Trip trip) {
        if (trip == null) {
            return null;
        }
        return modelMapper.map(trip, TripDTO.class);
    }

    @Override
    public Trip mapToEntity(TripDTO tripDTO) {
        if (tripDTO == null) {
            return null;
        }
        return modelMapper.map(tripDTO, Trip.class);
    }

    @Override
    public void updateEntityFromDTO(TripDTO tripDTO, Trip trip) {
        if (tripDTO == null || trip == null) {
            return;
        }
        modelMapper.map(tripDTO, trip); // Updates the existing trip entity with DTO values
    }

    @Override
    public ProfileTripDetailsDTO toDTO(Trip trip) {
        return modelMapper.map(trip, ProfileTripDetailsDTO.class);
    }
}
