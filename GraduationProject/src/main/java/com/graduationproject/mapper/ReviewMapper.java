package com.graduationproject.mapper;

import com.graduationproject.DTOs.ProfileReviewsDTO;
import com.graduationproject.DTOs.ReviewDTO;
import com.graduationproject.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {
    ProfileReviewsDTO toProfileReviewsDTO(Review review);
    Review toEntity(ReviewDTO reviewDTO);
    void updateReviewFromDTO(@MappingTarget Review review, ReviewDTO reviewDTO);
}