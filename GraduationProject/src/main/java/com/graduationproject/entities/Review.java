package com.graduationproject.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rate;

    @NotBlank
    @Size(max = 255, message = "Comment length must be at most 255 characters")
    private String comment;


    @ManyToOne
    @JoinColumn(name = "reviewer_id")
    @JsonBackReference
    private User reviewer;


    @ManyToOne
    @JoinColumn(name = "reviewee_id")
    @JsonBackReference
    private User reviewee;
}