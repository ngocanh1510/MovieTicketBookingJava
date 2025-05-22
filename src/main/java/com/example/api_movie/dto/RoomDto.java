package com.example.api_movie.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private String theaterName;
    private String roomName;
    private int capacity;
}
