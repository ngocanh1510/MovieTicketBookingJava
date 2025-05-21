package com.example.api_movie.controller;

import com.example.api_movie.dto.SeatDto;
import com.example.api_movie.dto.ShowtimeDto;
import com.example.api_movie.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
    @Autowired
    private SeatService seatService;

    @GetMapping("/roomName/{roomName}")
    public List<SeatDto> getSeatsByRoomName(@PathVariable String roomName) {
        return seatService.findSeatsByRoomName(roomName);
    }
}
