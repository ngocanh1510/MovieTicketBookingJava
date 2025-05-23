package com.example.api_movie.service;

import com.example.api_movie.dto.ShowtimeDto;
import com.example.api_movie.dto.ShowtimeResponseDto;
import com.example.api_movie.model.Movie;
import com.example.api_movie.model.Room;
import com.example.api_movie.model.Showtime;
import com.example.api_movie.repository.MovieRepository;
import com.example.api_movie.repository.RoomRepository;
import com.example.api_movie.repository.ShowtimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ShowtimeService {
    @Autowired
    private ShowtimeRepository showtimeRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RoomRepository roomRepository;

    // Lấy toàn bộ thông tin suất chiếu
    public List<ShowtimeResponseDto> getAllShowtimes() {
        return showtimeRepository.findAllByOrderByMovieTitleAsc().stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    // Lấy thông tin suất chiếu theo tên phim
    public List<ShowtimeResponseDto> getShowtimeByMovieTitle(String title){
        return showtimeRepository.findShowtimeByMovie_Title(title).stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    // Thêm suất chiếu cho admin
    public ShowtimeDto addShowtime(ShowtimeDto showtimeDto){
        Showtime showtime = new Showtime();

        //Tìm phim
        Movie movie = movieRepository.findById(showtimeDto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy movie với id: " + showtimeDto.getMovieId()));
        showtime.setMovie(movie);

        //Tìm phòng
        Room room = roomRepository.findById(showtimeDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng với id: " + showtimeDto.getRoomId()));
        showtime.setRoom(room);
        showtime.setDate(showtimeDto.getDate());
        showtime.setTime(showtimeDto.getTime());
        showtime.setLanguage(showtimeDto.getLanguage());

        Showtime savedShowtime = showtimeRepository.save(showtime);
        return convertToRequestDto(savedShowtime);
    }

    // Cập nhật suất chiếu cho admin
    public ShowtimeDto updateShowtime(int id, ShowtimeDto showtimeDto){
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy showtime có id: " + id));
        Movie movie = movieRepository.findById(showtimeDto.getMovieId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy movie có id: " + id));
        showtime.setMovie(movie);

        //Tìm phòng
        Room room= roomRepository.findById(showtimeDto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy room có id: " + id));
        showtime.setRoom(room);
        showtime.setDate(showtimeDto.getDate());
        showtime.setTime(showtimeDto.getTime());
        showtime.setLanguage(showtimeDto.getLanguage());

        Showtime savedShowtime = showtimeRepository.save(showtime);
        return convertToRequestDto(savedShowtime);
    }

    // Xóa suất chiếu cho admin
    public void deleteMovie(int id) {
        if (!showtimeRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy showtime có id: " + id);
        }
        showtimeRepository.deleteById(id);
    }

    private ShowtimeResponseDto convertToResponseDto(Showtime showtime) {
        return new ShowtimeResponseDto(
                showtime.getMovie().getTitle(),
                showtime.getRoom().getName(),
                showtime.getDate(),
                showtime.getTime(),
                showtime.getLanguage()
        );}

    private ShowtimeDto convertToRequestDto(Showtime showtime) {
        return new ShowtimeDto(
                showtime.getMovie().getId(),
                showtime.getMovie().getTitle(),
                showtime.getRoom().getId(),
                showtime.getRoom().getName(),
                showtime.getDate(),
                showtime.getTime(),
                showtime.getLanguage()
        );
    }

}