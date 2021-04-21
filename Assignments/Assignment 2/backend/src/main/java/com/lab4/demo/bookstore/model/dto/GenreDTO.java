package com.lab4.demo.bookstore.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GenreDTO {

    private Long id;
    private String genre;
}
