package com.example.api_movie.controller;

import com.example.api_movie.DatVe.CouponService;
import com.example.api_movie.dto.CouponDto;
import com.example.api_movie.dto.MovieDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/coupons")
@RestController
public class CouponController {
    @Autowired
    private CouponService couponService;
    @GetMapping
    public List<CouponDto> getAllCoupons() {
        return couponService.findAllCoupons();
    }
}
