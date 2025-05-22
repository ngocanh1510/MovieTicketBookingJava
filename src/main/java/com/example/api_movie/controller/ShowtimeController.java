package com.example.api_movie.controller;

import com.example.api_movie.dto.ShowtimeDto;
import com.example.api_movie.dto.ShowtimeResponseDto;
import com.example.api_movie.service.ShowtimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/showtimes")
public class ShowtimeController {

    @Autowired
    private ShowtimeService showtimeService;

    // Lấy tất cả suất chiếu
    @GetMapping
    public List<ShowtimeResponseDto> getAllShowtimes() {
        return showtimeService.getAllMovies();
    }

    // Tìm suất chiếu theo tên phim
    @GetMapping("/title/{title}")
    public List<ShowtimeResponseDto> getShowtimeByMovieTitle(@PathVariable String title) {
        return showtimeService.getShowtimeByMovieTitle(title);
    }

    //Thêm mới suất chiếu
    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<ShowtimeDto> addShowtime(@RequestBody ShowtimeDto showtimeDto) {
        ShowtimeDto createdShowtime = showtimeService.addShowtime(showtimeDto);
        return ResponseEntity.ok(createdShowtime);
    }

    // Cập nhật suất chiếu theo ID
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateShowtime(@PathVariable int id, @RequestBody ShowtimeDto showtimeDto) {
        try {
            ShowtimeDto updatedShowtime = showtimeService.updateShowtime(id, showtimeDto);
            return ResponseEntity.ok(updatedShowtime);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi server: " + e.getMessage()));
        }
    }

    // Xoá suất chiếu theo ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteShowtime(@PathVariable int id) {
        try {
            showtimeService.deleteMovie(id);
            return ResponseEntity.ok(Map.of("message", "Showtime đã được xoá thành công."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi server: " + e.getMessage()));
        }
    }
}
