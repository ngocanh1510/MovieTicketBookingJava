package com.example.api_movie.model;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.IOException;


@Entity
@Table(name = "Food_Beverage")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int price;
    private String detail;
    private String img;

    @Enumerated(EnumType.STRING)
    private Category category;

    public enum Category {
        BAP_NUOC,
        NUOC_GIAI_KHAT,
        SNACK
    }
}
