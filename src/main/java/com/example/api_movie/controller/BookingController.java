package com.example.api_movie.controller;

import com.example.api_movie.DatVe.BookingService;
import com.example.api_movie.dto.BookingDto;
import com.example.api_movie.model.Booking;
import com.example.api_movie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/booking")
public class BookingController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;

    @PostMapping
    @PreAuthorize("hasRole('admin') or hasRole('user')")
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDto bookingDto, Authentication authentication) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = userService.findByUsername(username).getId();
        bookingDto.setUserId(userId);

        Booking createdBooking = bookingService.createBooking(bookingDto);
        return ResponseEntity.ok(createdBooking);
    }
}
