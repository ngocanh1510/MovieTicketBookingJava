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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        //Set<FoodBookingKey> keys = new HashSet<>();
        List<FoodBooking> foodBookingList = new ArrayList<>();
        BigDecimal foodTotal = BigDecimal.ZERO;

        for (FoodBookingRequest foodRequest : request.getFoodBookings()) {
//            FoodBookingKey key = new FoodBookingKey(foodRequest.getFoodId(), booking.getId());
//
//            if (!keys.add(key)) {
//                throw new RuntimeException("Duplicate food booking detected.");
//            }

            Food food = foodRepository.findById(foodRequest.getFoodId())
                    .orElseThrow(() -> new RuntimeException("Food not found"));

            FoodBooking foodBooking = new FoodBooking();
            foodBooking.setId(new FoodBookingKey(food.getId(), booking.getId()));
            foodBooking.setFood(food);
            foodBooking.setBooking(booking);
            foodBooking.setQuantity(foodRequest.getQuantity());

            foodBookingList.add(foodBooking);

            // Tính tiền thức ăn
            foodTotal = foodTotal.add(food.getPrice())
                    .multiply(BigDecimal.valueOf(foodRequest.getQuantity()));

        }

        booking.setFoodBookings(foodBookingList);
        //foodBookingRepository.saveAll(foodBookingList);

        //Tính tổng tiền
        booking.setTotal(ticketTotal.add(foodTotal));

        return bookingRepository.save(booking);
    }

    public Booking updateStatus(int bookingId, String newStatus) {
        // Tìm booking theo id
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + bookingId));

        // Cập nhật trạng thái mới
        booking.setPaymentStatus(newStatus);

        // Lưu lại booking
        return bookingRepository.save(booking);
    }
}
