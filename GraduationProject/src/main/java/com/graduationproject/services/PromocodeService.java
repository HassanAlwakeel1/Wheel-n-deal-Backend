package com.graduationproject.services;

public interface PromocodeService {
    public void generatePromoCode(Integer orderId);
    public Boolean checkPromoCode(String promoCode);
}