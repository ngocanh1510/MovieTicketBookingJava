package com.example.api_movie.service;

import com.example.api_movie.dto.TheaterDto;
import com.example.api_movie.model.Brand;
import com.example.api_movie.model.Theater;
import com.example.api_movie.repository.BrandRepository;
import com.example.api_movie.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheaterService {
    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private BrandRepository brandRepository;

    public List<TheaterDto> getAllTheaters() {
        return theaterRepository.findAllByOrderByNameAsc().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public TheaterDto addTheater(TheaterDto theaterDto) {
        Theater theater = new Theater();

        Brand brand =brandRepository.findById(theaterDto.getBrand_id())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy brand có id: " + theaterDto.getBrand_id()));
        theater.setBrand(brand);
        theater.setName(theaterDto.getName());
        theater.setLocation(theaterDto.getLocation());
        theaterRepository.save(theater);
        return convertToRequestDto(theater);
    }

    public TheaterDto updateTheater(int id,TheaterDto theaterDto) {
        Theater theater = theaterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy theater có id: " + id));
        Brand brand =brandRepository.findById(theaterDto.getBrand_id())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy brand có id: " + theaterDto.getBrand_id()));
        theater.setBrand(brand);
        theater.setName(theaterDto.getName());
        theater.setLocation(theaterDto.getLocation());
        theaterRepository.save(theater);
        return convertToRequestDto(theater);
    }

    public void deleteTheater(int id) {
        if(!theaterRepository.existsById(id)) {
            throw  new RuntimeException("Không tìm thấy theater có id: " + id);
        }
        theaterRepository.deleteById(id);
    }

    private TheaterDto convertToDto(Theater theater) {
        return new TheaterDto(
                theater.getName(),
                theater.getLocation(),
                theater.getBrand().getName()
        );
    }
    private TheaterDto convertToRequestDto(Theater theater) {
        return new TheaterDto(
                theater.getBrand().getId(),
                theater.getBrand().getName(),
                theater.getName(),
                theater.getLocation()
        );
    }
}
