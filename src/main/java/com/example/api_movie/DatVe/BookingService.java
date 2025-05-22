package com.example.api_movie.DatVe;

import com.example.api_movie.dto.BookingDto;
import com.example.api_movie.dto.FoodBookingRequest;
import com.example.api_movie.dto.TicketDto;
import com.example.api_movie.model.*;
import com.example.api_movie.repository.BookingRepository;
import com.example.api_movie.repository.FoodBookingRepository;
import com.example.api_movie.repository.FoodRepository;
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

    @Autowired
    private FoodBookingRepository foodBookingRepository;

    @Autowired
    private FoodRepository foodRepository;

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

        //Tính tiền vé
        BigDecimal ticketTotal = tickets.stream()
                .map(Ticket::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        booking.setTickets(tickets);

        List<FoodBooking> foodBookingList = new ArrayList<>();
        BigDecimal foodTotal = BigDecimal.ZERO;

        for (FoodBookingRequest foodRequest : request.getFoodBookings()) {
            Food food = foodRepository.findById(foodRequest.getFoodId())
                    .orElseThrow(() -> new RuntimeException("Food not found"));

            FoodBooking foodBooking = new FoodBooking();
            foodBooking.setId(new FoodBookingKey(food.getId(), booking.getId()));
            foodBooking.setFood(food);
            foodBooking.setBooking(booking);
            foodBooking.setQuantity(foodRequest.getQuantity());

            foodBookingList.add(foodBooking);

            // Tính tiền thức ăn
            foodTotal = foodTotal.add(BigDecimal.valueOf(food.getPrice())
                    .multiply(BigDecimal.valueOf(foodRequest.getQuantity())));

        }

        foodBookingRepository.saveAll(foodBookingList);

        //Tính tổng tiền
        booking.setTotal(ticketTotal.add(foodTotal));

        return bookingRepository.save(booking);
    }
}
