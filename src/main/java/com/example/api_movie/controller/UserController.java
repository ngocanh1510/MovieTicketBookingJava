package com.example.api_movie.controller;

import com.example.api_movie.dto.UserDto;
import com.example.api_movie.model.User;
import com.example.api_movie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // API lấy thông tin cá nhân của người dùng
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.getCurrentUser(username);
        return ResponseEntity.ok(userDto);
    }

    // API cập nhật thông tin cá nhân của người dùng
    @PutMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody UserDto updatedUserDto) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            UserDto updatedUser = userService.updateUser(username, updatedUserDto);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }

    }
}
