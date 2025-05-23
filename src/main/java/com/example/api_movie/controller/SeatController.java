package com.example.api_movie.controller;

import com.example.api_movie.dto.FoodDto;
import com.example.api_movie.dto.SeatDto;
import com.example.api_movie.model.Seat;
import com.example.api_movie.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
    @Autowired
    private SeatService seatService;

    // API lấy thông tin ghế theo tên phòng
    @GetMapping("/{roomName}")
    public List<SeatDto> getSeatsByRoomName(@PathVariable String roomName) {
        return seatService.findSeatsByRoomName(roomName);
    }

    // API thêm ghế của admin
    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<SeatDto> createSeat(@RequestBody SeatDto seatDto) {
        SeatDto createdSeat = seatService.addSeat(seatDto);
        return ResponseEntity.ok(createdSeat);
    }

    // API cập nhật ghế của admin
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateSeat(@PathVariable int id,@RequestBody SeatDto seatDto) {
        try {
            SeatDto updatedSeat = seatService.updateSeat(id, seatDto);
            return ResponseEntity.ok(updatedSeat);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi server: " + e.getMessage()));
        }
    }

    // API xóa ghế của admin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteSeat(@PathVariable int id) {
        try {
            seatService.deleteSeat(id);
            return ResponseEntity.ok(Map.of("message", "Ghế đã được xoá thành công."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi server: " + e.getMessage()));
        }
    }
}
