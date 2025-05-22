package com.example.api_movie.service;

import com.example.api_movie.dto.RoomDto;
import com.example.api_movie.model.Room;
import com.example.api_movie.model.Theater;
import com.example.api_movie.repository.RoomRepository;
import com.example.api_movie.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;
    private TheaterRepository theaterRepository;

    public List<RoomDto> getAllRooms() {
        return roomRepository.findAllByOrderByTheaterNameAsc().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<RoomDto> getRoomByTheaterName(String theaterName) {
        return roomRepository.findByTheaterName(theaterName).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public RoomDto addRoom(RoomDto roomDto) {
        Room room = new Room();
        room.setName(roomDto.getRoomName());

        Theater theater = theaterRepository.findByName(roomDto.getTheaterName());
        if (theater == null) {
            throw new RuntimeException("Theater not found with name: " + roomDto.getTheaterName());
        }
        room.setTheater(theater);
        room.setCapacity(roomDto.getCapacity());

        roomRepository.save(room);
        return convertToDto(room);
    }

    public RoomDto updateRoom(int id, RoomDto roomDto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng có id: " + id));
        room.setName(roomDto.getRoomName());
        Theater theater = theaterRepository.findByName(roomDto.getTheaterName());
        if (theater == null) {
            throw new RuntimeException("Theater not found with name: " + roomDto.getTheaterName());
        }
        room.setTheater(theater);
        room.setCapacity(roomDto.getCapacity());

        roomRepository.save(room);
        return convertToDto(room);
    }
    private RoomDto convertToDto(Room room) {
        return new RoomDto(
                room.getTheater().getName(),
                room.getName(),
                room.getCapacity()
        );
    }
}
