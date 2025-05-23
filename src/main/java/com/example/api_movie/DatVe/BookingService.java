package com.example.api_movie.DatVe;

import com.example.api_movie.dto.*;
import com.example.api_movie.model.*;
import com.example.api_movie.repository.*;
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

    @Autowired
    private ShowtimeRepository showtimeRepository;

    @Autowired
    private SeatRepository seatRepository;

    //Đặt vé
    public BookingResponseDto createBooking(BookingDto request) {
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

        booking.setFoodBookings(foodBookingList);
        //foodBookingRepository.saveAll(foodBookingList);

        //Tính tổng tiền
        booking.setTotal(ticketTotal.add(foodTotal));
        bookingRepository.save(booking);
        return mapToResponseDto(booking, tickets, foodBookingList);
    }

    //Lấy thông tin đặt vé của user
    public List<BookingResponseDto> getBookingsByUserId(Integer userId) {
        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return bookings.stream().map(booking -> {
            List<Ticket> tickets = booking.getTickets();
            List<FoodBooking> foodBookings = booking.getFoodBookings();
            return mapToResponseDto(booking, tickets, foodBookings);
        }).toList();
    }

    //Lấy thông tin đặt vé của adin
    public List<BookingResponseDto> getBookingsByAdminId(Integer userId) {
        List<Booking> bookings = bookingRepository.findAll();

        return bookings.stream().map(booking -> {
            List<Ticket> tickets = booking.getTickets();
            List<FoodBooking> foodBookings = booking.getFoodBookings();
            return mapToResponseDto(booking, tickets, foodBookings);
        }).toList();
    }

    private BookingResponseDto mapToResponseDto(Booking booking, List<Ticket> tickets, List<FoodBooking> foodBookingList) {
        BookingResponseDto dto = new BookingResponseDto();
        dto.setId(booking.getId());
        dto.setPaymentStatus(booking.getPaymentStatus());
        dto.setTotal(booking.getTotal());

        // Map vé
        List<TicketResponseDto> ticketDtos = tickets.stream().map(ticket -> {
            TicketResponseDto t = new TicketResponseDto();
            t.setId(ticket.getId());
            t.setPrice(ticket.getPrice());

            t.setSeatName(seatRepository.findById(ticket.getSeatId())
                    .map(seat -> seat.getRow() + String.valueOf(seat.getNumber()))
                    .orElse("Unknown"));

            // Lấy showtime
            ShowtimeResponseDto showtimeDto = showtimeRepository.findById(ticket.getShowtimeId())
                    .map(showtime -> {
                        ShowtimeResponseDto showtimeResponseDto = new ShowtimeResponseDto();
                        showtimeResponseDto.setMovieTitle(showtime.getMovie().getTitle());
                        showtimeResponseDto.setRoomName(showtime.getRoom().getName());
                        showtimeResponseDto.setDate(showtime.getDate());
                        showtimeResponseDto.setTime(showtime.getTime());
                        showtimeResponseDto.setLanguage(showtime.getLanguage());
                        return showtimeResponseDto;
                    })
                    .orElse(null);

            t.setShowtime(showtimeDto);
            return t;
        }).toList();
        dto.setTickets(ticketDtos);

        dto.setFoodBookings(foodBookingList);

        return dto;
    }
}

