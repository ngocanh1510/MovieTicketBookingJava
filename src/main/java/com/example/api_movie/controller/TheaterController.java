package com.example.api_movie.controller;


import com.example.api_movie.dto.TheaterDto;
import com.example.api_movie.service.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {
    @Autowired
    private TheaterService theaterService;

    @GetMapping
    public List<TheaterDto> getAllTheaters() {
        return theaterService.getAllTheaters();
    }

    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<TheaterDto> createTheater(@RequestBody TheaterDto theaterDto) {
        TheaterDto createdtheater = theaterService.addTheater(theaterDto);
        return ResponseEntity.ok(createdtheater);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateTheater(@PathVariable int id,@RequestBody TheaterDto theaterDto){
        try {
            TheaterDto updatedtheater = theaterService.updateTheater(id, theaterDto);
            return ResponseEntity.ok(updatedtheater);
        } catch (RuntimeException e) {
             return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi server: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteTheater(@PathVariable int id) {
        try {
            theaterService.deleteTheater(id);
            return ResponseEntity.ok(Map.of("message", "Theater đã được xoá thành công."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi server: " + e.getMessage()));
        }
    }

}

