package com.graduationproject.mapper;

import com.graduationproject.DTOs.stripePaymentDTOs.ChargeUserDTO;
import com.graduationproject.entities.StripePaymentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StripePaymentMapper {
    StripePaymentEntity toEntity(ChargeUserDTO chargeUserDTO);
}
