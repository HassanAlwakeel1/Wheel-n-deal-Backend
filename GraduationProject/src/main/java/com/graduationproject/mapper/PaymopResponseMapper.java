package com.graduationproject.mapper;

import com.graduationproject.DTOs.paymobPaymentDTOs.PayResponseDTO;
import com.graduationproject.entities.PaymobResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymopResponseMapper {
    PaymobResponse toEntity(PayResponseDTO payResponseDTO);
    PayResponseDTO toDTO(PaymobResponse paymobResponse);
}
