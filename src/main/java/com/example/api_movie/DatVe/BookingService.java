package com.example.api_movie.DatVe;

import com.example.api_movie.dto.BookingDto;
import com.example.api_movie.dto.TicketDto;
import com.example.api_movie.model.Booking;
import com.example.api_movie.model.Ticket;
import com.example.api_movie.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TicketService ticketService;

    public Booking createBooking(BookingDto request) {
        Booking booking = new Booking();
        booking.setUserId(request.getUserId());
        booking.setPaymentStatus("pending");
        booking.setTotal(BigDecimal.ZERO);
        // Lưu booking trước để lấy ID
        booking = bookingRepository.save(booking);

        List<Ticket> tickets = new ArrayList<>();
        for (TicketDto dto : request.getTickets()) {
            Ticket ticket = ticketService.createTicket(dto, request.getUserId(), booking);
            tickets.add(ticket);
        }

        booking.setTickets(tickets);

        // Tính tổng tiền
        BigDecimal total = tickets.stream()
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        booking.setTotal(total);

        return bookingRepository.save(booking);
    }
}
