package com.example.api_movie.service;

import com.example.api_movie.dto.UserDto;
import com.example.api_movie.model.User;
import com.example.api_movie.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto getCurrentUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserDto(
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getDateOfBirth(),
                user.getGender(),
                user.getAvatar()
        );
    }

    public UserDto updateUser(String username, UserDto updatedUserDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(updatedUserDto.getName());
        user.setEmail(updatedUserDto.getEmail());
        user.setPhoneNumber(updatedUserDto.getPhoneNumber());
        user.setDateOfBirth(updatedUserDto.getDateOfBirth());
        user.setGender(updatedUserDto.getGender());
        user.setAvatar(updatedUserDto.getAvatar());

        userRepository.save(user);

        return new UserDto(
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getDateOfBirth(),
                user.getGender(),
                user.getAvatar()
        );

    }
}
