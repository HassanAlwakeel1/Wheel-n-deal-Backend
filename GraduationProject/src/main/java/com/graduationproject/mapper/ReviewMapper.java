package com.graduationproject.mapper;

import com.graduationproject.DTOs.ProfileReviewsDTO;
import com.graduationproject.DTOs.ReviewDTO;
import com.graduationproject.entities.Review;

public interface ReviewMapper {
    ProfileReviewsDTO toProfileReviewsDTO(Review review);
    Review toEntity(ReviewDTO reviewDTO);

    void updateReviewFromDTO(Review review, ReviewDTO reviewDTO);
}
