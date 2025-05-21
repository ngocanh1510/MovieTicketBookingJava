package com.example.api_movie.controller;

import com.example.api_movie.DatVe.TicketService;
import com.example.api_movie.dto.MovieDto;
import com.example.api_movie.dto.TicketDto;
import com.example.api_movie.model.Booking;
import com.example.api_movie.model.Ticket;
import com.example.api_movie.repository.BookingRepository;
import com.example.api_movie.service.MovieService;
import com.example.api_movie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/ticket")
public class TicketController {
    @Autowired
    private TicketService ticketService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping
    @PreAuthorize("hasRole('admin') or hasRole('user')")
    public ResponseEntity<?> createTicket(@RequestBody TicketDto ticketDto,  Authentication authentication) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        int userId = userService.findByUsername(username).getId();
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setTotal(ticketDto.getPrice());
        booking.setPaymentStatus("PENDING"); // hoặc đã thanh toán nếu cần
        booking = bookingRepository.save(booking);
        Ticket createdTicket = ticketService.createTicket(ticketDto, userId, booking);
        return ResponseEntity.ok(createdTicket);
    }
}
