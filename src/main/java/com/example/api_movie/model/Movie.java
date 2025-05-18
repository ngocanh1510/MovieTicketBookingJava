package com.example.api_movie.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "Movies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    private int duration;
    private String genre;

    @Column(name = "limit_age")
    private int limitAge;

    private String poster;
    private double rating;

    @Column(name = "film_director")
    private String filmDirector;

    @Column(name = "description_movie")
    private String descriptionMovie;

    @Column(name = "cast_movie")
    private String castMovie;

    @Column(name = "vid_url")
    private String vidUrl;
}
