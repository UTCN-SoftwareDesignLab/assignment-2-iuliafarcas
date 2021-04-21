package com.lab4.demo.bookstore.mapper;

import com.lab4.demo.bookstore.model.dto.GenreDTO;
import com.lab4.demo.bookstore.model.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {

    GenreDTO toDTO(Genre genre);
    Genre fromDTO(GenreDTO genreDTO);
}
