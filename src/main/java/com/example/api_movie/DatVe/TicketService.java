package com.example.api_movie.DatVe;

import com.example.api_movie.dto.TicketDto;
import com.example.api_movie.model.Booking;
import com.example.api_movie.model.Seat;
import com.example.api_movie.model.Showtime;
import com.example.api_movie.model.Ticket;
import com.example.api_movie.repository.SeatRepository;
import com.example.api_movie.repository.ShowtimeRepository;
import com.example.api_movie.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private ShowtimeRepository showtimeRepository;

    public Ticket createTicket(TicketDto dto, int userId, Booking booking) {
        Seat seat = seatRepository.findById(dto.getSeatId())
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        Showtime showtime = showtimeRepository.findById(dto.getShowtimeId())
                .orElseThrow(()->new RuntimeException("Không tìm thấy showtime"));
        if (seat.getStatus() != Seat.Status.available) {
            throw new RuntimeException("Seat is not available");
        }

        seat.setStatus(Seat.Status.booked);
        seatRepository.save(seat);

        Ticket ticket = new Ticket();
        ticket.setSeatId(dto.getSeatId());
        ticket.setShowtime(showtime);
        ticket.setPrice(seat.getPrice());
        ticket.setUserId(userId);
        ticket.setBooking(booking);

        Ticket savedTicket = ticketRepository.save(ticket);

        return  savedTicket;
    }

    private TicketDto convertToDto(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setId(ticket.getId());
        dto.setSeatId(ticket.getSeatId());
        dto.setShowtimeId(ticket.getShowtime().getId());
        dto.setPrice(ticket.getPrice());
        dto.setUserId(ticket.getUserId());
        dto.setBookingId(ticket.getBooking().getId());
        return dto;
    }
}