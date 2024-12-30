package com.graduationproject.services;

import com.graduationproject.DTOs.ReviewDTO;
import org.springframework.http.ResponseEntity;

public interface ReviewService {
    ResponseEntity<?> submitOrEditReview(ReviewDTO reviewDTO);
    ResponseEntity<?> deleteReview(int reviewId);
}