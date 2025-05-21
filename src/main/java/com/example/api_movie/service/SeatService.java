package com.example.api_movie.service;

import com.example.api_movie.dto.SeatDto;
import com.example.api_movie.dto.ShowtimeDto;
import com.example.api_movie.model.Seat;
import com.example.api_movie.model.Showtime;
import com.example.api_movie.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;

    public List<SeatDto> findSeatsByRoomName(String roomName) {
        return seatRepository.findSeatsByRoom_Name(roomName).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private SeatDto convertToDto(Seat seat) {
        return new SeatDto(
            seat.getId(),
            seat.getRoom().getName(),
            seat.getRow(),
            seat.getNumber(),
            seat.getStatus(),
            seat.getPrice()
        );
    }
}
