package com.example.api_movie.controller;

import com.example.api_movie.dto.FoodDto;
import com.example.api_movie.model.Food;
import com.example.api_movie.DatVe.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/foods")
public class FoodController {
    @Autowired
    private FoodService foodService;

    // API lấy toàn bộ thông tin thức ăn
    @GetMapping
    public List<FoodDto> getAll() {
        return foodService.findAll();
    }

    // API lấy thông tin thức ăn theo loại
    @GetMapping("/{category}")
    public List<FoodDto> getByCategory(@PathVariable Food.Category category) {
        return foodService.findByCategory(category);
    }

    // API thêm thức ăn của admin
    @PostMapping
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<FoodDto> createFood(@RequestBody FoodDto foodDto) {
        FoodDto createdFood = foodService.addFood(foodDto);
        return ResponseEntity.ok(createdFood);
    }

    // API cập nhật thức ăn của admin
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> updateFood(@PathVariable int id,@RequestBody FoodDto foodDto) {
        try {
            FoodDto updatedFood = foodService.updateFood(id, foodDto);
            return ResponseEntity.ok(updatedFood);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi server: " + e.getMessage()));
        }
    }

    // API xóa thức ăn của admin
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<?> deleteFood(@PathVariable int id) {
        try {
            foodService.deleteFood(id);
            return ResponseEntity.ok(Map.of("message", "Món ăn đã được xoá thành công."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Lỗi server: " + e.getMessage()));
        }
    }
}
