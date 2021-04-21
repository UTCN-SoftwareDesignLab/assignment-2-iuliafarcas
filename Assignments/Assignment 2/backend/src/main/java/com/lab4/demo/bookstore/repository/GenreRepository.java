package com.lab4.demo.bookstore.repository;

import com.lab4.demo.bookstore.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByGenre(String gen);
}
