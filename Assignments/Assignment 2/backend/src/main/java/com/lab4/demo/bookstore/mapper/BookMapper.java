package com.lab4.demo.bookstore.mapper;

import com.lab4.demo.bookstore.model.dto.BookDTO;
import com.lab4.demo.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    //@Mapping(source = "genre.genre", target = "genre")
    BookDTO bookToDTO(Book book);

    //@Mapping(source = "genre", target = "genre.genre")
    Book bookFromDTO(BookDTO book);
}
