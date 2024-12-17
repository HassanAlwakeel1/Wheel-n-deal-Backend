package com.graduationproject.mapper.impl;

import com.graduationproject.DTOs.ProfileReviewsDTO;
import com.graduationproject.DTOs.ReviewDTO;
import com.graduationproject.entities.Review;
import com.graduationproject.mapper.ReviewMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ReviewMapperImpl implements ReviewMapper {
    private final ModelMapper modelMapper;

    public ReviewMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfileReviewsDTO toProfileReviewsDTO(Review review) {
        return modelMapper.map(review, ProfileReviewsDTO.class);
    }

    /**
     * Maps ReviewDTO to a new Review entity.
     * Used for creating a new Review from a DTO.
     */
    @Override
    public Review toEntity(ReviewDTO reviewDTO) {
        return modelMapper.map(reviewDTO, Review.class);
    }

    /**
     * Maps ReviewDTO to an existing Review entity.
     * Used for updating an existing Review with data from a DTO.
     */
    @Override
    public void updateReviewFromDTO(Review review, ReviewDTO reviewDTO) {
        modelMapper.map(reviewDTO,review);
    }
}
