package com.example.api_movie.service;

import com.example.api_movie.dto.SeatDto;
import com.example.api_movie.model.Room;
import com.example.api_movie.model.Seat;
import com.example.api_movie.repository.RoomRepository;
import com.example.api_movie.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private RoomRepository roomRepository;

    public List<SeatDto> findSeatsByRoomName(String roomName) {
        return seatRepository.findSeatsByRoom_Name(roomName).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public SeatDto addSeat(SeatDto seatDto) {
        Seat seat = new Seat();
        Room room = roomRepository.findById(seatDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng có id" + seatDto.getRoomId()));
        seat.setRoom(room);
        seat.setRow(seatDto.getRow());
        seat.setNumber(seatDto.getNumber());
        seat.setStatus(seatDto.getStatus());
        seat.setPrice(seatDto.getPrice());
        seatRepository.save(seat);
        return convertToDto(seat);
    }

    public SeatDto updateSeat(int id,SeatDto seatDto) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Không tìm thấy ghế"));
        Room room = roomRepository.findById(seatDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng có id" + seatDto.getRoomId()));
        seat.setRoom(room);
        seat.setRow(seatDto.getRow());
        seat.setNumber(seatDto.getNumber());
        seat.setStatus(seatDto.getStatus());
        seat.setPrice(seatDto.getPrice());
        seatRepository.save(seat);
        return convertToDto(seat);
    }

    public void deleteSeat(int id) {
        if(!seatRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy ghế");
        }
        seatRepository.deleteById(id);
    }
    private SeatDto convertToDto(Seat seat) {
        return new SeatDto(
            seat.getRoom().getId(),
            seat.getRoom().getName(),
            seat.getRow(),
            seat.getNumber(),
            seat.getStatus(),
            seat.getPrice()
        );
    }
}
