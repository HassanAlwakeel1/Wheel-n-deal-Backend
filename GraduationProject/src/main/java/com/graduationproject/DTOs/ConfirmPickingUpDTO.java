package com.graduationproject.DTOs;

import com.graduationproject.enums.PaymentMethod;
import lombok.Data;

@Data
public class ConfirmPickingUpDTO {
    private String promoCode;
    private PaymentMethod paymentMethod;
    private Integer commuterId;
    //I will get the UserId from the orderId
    private Integer orderId;
    private Double price;
}
