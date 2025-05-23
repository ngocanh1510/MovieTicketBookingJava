package com.example.api_movie.DatVe;

import com.example.api_movie.dto.CouponDto;
import com.example.api_movie.model.Coupon;
import com.example.api_movie.repository.CouponRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PromotionCode;
import com.stripe.param.PromotionCodeListParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;

    public List<CouponDto> findAllCoupons(){
        return couponRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private CouponDto convertToDto(Coupon coupon) {
        return new CouponDto(
                coupon.getCouponCode(),
                coupon.getBalance(),
                coupon.getExpiry()
        );
    }

    public String getValidPromotionCodeId(String couponCode) throws StripeException {
        PromotionCodeListParams promoParams = PromotionCodeListParams.builder()
                .setCode(couponCode)
                .setActive(true)
                .setLimit(1L)
                .build();

        List<PromotionCode> codes = PromotionCode.list(promoParams).getData();
        if (codes.isEmpty()) {
            throw new RuntimeException("Mã giảm giá không hợp lệ");
        }
        return codes.get(0).getId();
    }
}
