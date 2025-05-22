package com.example.api_movie.controller;

import com.example.api_movie.dto.RoomDto;
import com.example.api_movie.model.Room;
import com.example.api_movie.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping
    public List<RoomDto> getAllRooms() {
        return roomService.getAllRooms();
    }

    @GetMapping("/{theaterName}")
    public List<RoomDto> getRoomByTheaterName(@PathVariable("theaterName") String theaterName) {
        return roomService.getRoomByTheaterName(theaterName);
    }
}
