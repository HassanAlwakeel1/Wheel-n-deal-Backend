package com.graduationproject.mapper;

import com.graduationproject.DTOs.ProfileTripDetailsDTO;
import com.graduationproject.DTOs.TripDTO;
import com.graduationproject.entities.Trip;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TripMapper {
    TripDTO mapToDTO(Trip trip);
    Trip mapToEntity(TripDTO tripDTO);
    void updateEntityFromDTO(@MappingTarget Trip trip, TripDTO tripDTO);
    ProfileTripDetailsDTO toDTO(Trip trip);
}