package com.example.api_movie.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TheaterDto {
    private int brand_id;
    private String brand;
    private String name;
    private String location;

    public TheaterDto(String brand,String name, String location) {
        this.name = name;
        this.location = location;
        this.brand = brand;
    }
    public TheaterDto(int brand_id,String name, String location) {
        this.name = name;
        this.location = location;
        this.brand_id = brand_id;
    }
}
