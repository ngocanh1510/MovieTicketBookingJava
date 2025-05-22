package com.example.api_movie.controller;

import com.example.api_movie.dto.RoomDto;
import com.example.api_movie.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<RoomDto> addRoom(@RequestBody RoomDto roomDto) {
        RoomDto createdRoom = roomService.addRoom(roomDto);
        return ResponseEntity.ok(createdRoom);
    }

    // Cập nhật suất chiếu theo ID
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateRoom(@PathVariable int id, @RequestBody RoomDto roomDto) {
        try {
            RoomDto updatedRoom = roomService.updateRoom(id, roomDto);
            return ResponseEntity.ok(updatedRoom);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi server: " + e.getMessage()));
        }
    }

    // Xoá suất chiếu theo ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteRoom(@PathVariable int id) {
        try {
            roomService.deleteRoom(id);
            return ResponseEntity.ok(Map.of("message", "Room đã được xoá thành công."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi server: " + e.getMessage()));
        }
    }
}
