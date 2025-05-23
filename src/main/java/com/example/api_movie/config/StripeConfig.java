package com.example.api_movie.config;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class StripeConfig {

    @Value("${stripe.secretKey}")
    private String stripeSecretKey;

    @PostConstruct
    public void setup() {
        Stripe.apiKey = stripeSecretKey;
    }
}
