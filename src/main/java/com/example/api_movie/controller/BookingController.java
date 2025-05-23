package com.example.api_movie.controller;

import com.example.api_movie.DatVe.BookingService;
import com.example.api_movie.dto.BookingDto;
import com.example.api_movie.dto.BookingResponseDto;
import com.example.api_movie.dto.MovieDto;
import com.example.api_movie.model.Booking;
import com.example.api_movie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/booking")
public class BookingController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;

    // API đặt vé
    @PostMapping
    @PreAuthorize("hasRole('admin') or hasRole('user')")
    public ResponseEntity<?> createBooking(@RequestBody BookingDto bookingDto, Authentication authentication) {

        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            int userId = userService.findByUsername(username).getId();
            bookingDto.setUserId(userId);

            BookingResponseDto createdBooking = bookingService.createBooking(bookingDto);
            return ResponseEntity.ok(createdBooking);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi server: " + e.getMessage()));
        }
    }

    // API lấy thông tin đặt vé của user
    @GetMapping("/user")
    @PreAuthorize("hasRole('admin') or hasRole('user')")
    public List<BookingResponseDto> getBookingsByUser(Authentication authentication) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = userService.findByUsername(username).getId();
        return bookingService.getBookingsByUserId(userId);
    }

    // API lấy toàn bộ thông tin đặt vé của admin
    @GetMapping("/admin")
    @PreAuthorize("hasRole('admin')")
    public List<BookingResponseDto> getBookingsByAdmin(Authentication authentication) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = userService.findByUsername(username).getId();
        return bookingService.getBookingsByAdminId(userId);
    }
}
