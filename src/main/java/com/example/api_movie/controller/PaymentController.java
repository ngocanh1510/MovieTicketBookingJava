package com.example.api_movie.controller;

import com.example.api_movie.DatVe.BookingService;
import com.example.api_movie.DatVe.PaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private BookingService bookingService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-session")
    public Map<String, String> createStripeSession(@RequestBody Map<String, Object> requestBody,HttpServletRequest request) throws StripeException {
        Integer bookingId = Integer.parseInt(requestBody.get("bookingId").toString());
        String couponCode = requestBody.containsKey("couponCode") ? requestBody.get("couponCode").toString() : null;
        System.out.println("huhu"+ bookingId+" "+couponCode+" "+request);
        Session session = paymentService.createStripeSession(bookingId, couponCode,request);

        Map<String, String> response = new HashMap<>();
        response.put("sessionId", session.getId());
        response.put("url", session.getUrl());
        return response;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request) {
        try {
            paymentService.processWebhook(request);
            System.out.println("Webhook processed"+request);
            return ResponseEntity.ok("");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("");
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing webhook");
        }
    }
}
