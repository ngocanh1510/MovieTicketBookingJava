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
    @Autowired
    private TheaterRepository theaterRepository;

    // Lấy toàn bộ thông tin phòng
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAllByOrderByTheaterNameAsc().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Lấy thông tin phòng theo tên rạp
    public List<RoomDto> getRoomByTheaterName(String theaterName) {
        return roomRepository.findByTheaterName(theaterName).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Thêm phòng của admin
    public RoomDto addRoom(RoomDto roomDto) {
        Room room = new Room();
        room.setName(roomDto.getRoomName());

        Theater theater = theaterRepository.findById(roomDto.getTheaterId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy rạp có id: " + roomDto.getTheaterId()));
        room.setTheater(theater);
        room.setCapacity(roomDto.getCapacity());

        roomRepository.save(room);
        return convertToDto(room);
    }

    // Cập nhật phòng của admin
    public RoomDto updateRoom(int id, RoomDto roomDto) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng có id: " + id));
        room.setName(roomDto.getRoomName());
        Theater theater = theaterRepository.findById(roomDto.getTheaterId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy rạp có id: " + roomDto.getTheaterId()));
        room.setTheater(theater);
        room.setCapacity(roomDto.getCapacity());

        roomRepository.save(room);
        return convertToDto(room);
    }

    // Xóa phòng của admin
    public void deleteRoom(int id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy room có id: " + id);
        }
        roomRepository.deleteById(id);
    }
    private RoomDto convertToDto(Room room) {
        return new RoomDto(
                room.getTheater().getId(),
                room.getTheater().getName(),
                room.getName(),
                room.getCapacity()
        );
    }


}
