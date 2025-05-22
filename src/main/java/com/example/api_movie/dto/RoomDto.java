package com.example.api_movie.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private int theaterId;
    private String theaterName;
    private String roomName;
    private int capacity;

    public RoomDto(int theaterId, String roomName, int capacity) {
        this.theaterId = theaterId;
        this.roomName = roomName;
        this.capacity = capacity;
    }

    public RoomDto(String theaterName, String roomName, int capacity) {
        this.theaterName = theaterName;
        this.roomName = roomName;
        this.capacity = capacity;
    }
}
