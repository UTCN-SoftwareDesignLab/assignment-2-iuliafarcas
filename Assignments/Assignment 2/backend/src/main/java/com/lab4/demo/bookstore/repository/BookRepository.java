package com.lab4.demo.bookstore.repository;

import com.lab4.demo.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBookByAuthorOrTitleOrGenre(String author, String title, String genre);
}
