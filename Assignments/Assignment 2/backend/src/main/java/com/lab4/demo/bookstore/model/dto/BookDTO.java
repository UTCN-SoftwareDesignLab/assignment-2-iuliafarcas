package com.lab4.demo.bookstore.model.dto;

import com.lab4.demo.bookstore.model.Genre;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private String genre;
    private Float price;
    private Long stock;
}
