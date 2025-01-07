package com.graduationproject.DTOs.optDTOs;

import com.graduationproject.enums.OtpStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtpResponseDTO {
    private OtpStatus status;
    private String message;
}