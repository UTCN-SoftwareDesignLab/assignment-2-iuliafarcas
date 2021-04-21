package com.lab4.demo.bookstore;

import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.bookstore.mapper.GenreMapper;
import com.lab4.demo.bookstore.model.Book;
import com.lab4.demo.bookstore.model.Genre;
import com.lab4.demo.bookstore.model.dto.BookDTO;
import com.lab4.demo.bookstore.repository.BookRepository;
import com.lab4.demo.bookstore.repository.GenreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookServiceIntegrationTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GenreRepository genreRepository;

    @BeforeEach
    void setUp(){
        bookRepository.deleteAll();
        //genreRepository.deleteAll();
    }

    @Test
    void findAll() {
        int nrBooks = 10;
        List<Book> books = new ArrayList<>();
        for( int i = 0; i < nrBooks; i++)
        {
            Genre genre = Genre.builder().genre(" " + i).build();
            Book book = Book.builder()
                    .title("T" + i)
                    .author("A" + i)
                    //.genre(genre)
                    .genre("SF")
                    .price(100.0f)
                    .stock(Long.valueOf(i))
                    .build();
            books.add(book);
            //genreRepository.save(genre);
            //System.out.println(book.getAuthor() + " " + book.getTitle() + " " + book.getGenre());
            bookRepository.save(book);
        }

        List<BookDTO> all = bookService.findAll();
        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void deleteById() {
        //Genre genre = Genre.builder().genre("SF").build();
        BookDTO book = BookDTO.builder()
                .title("T")
                .author("A")
                .genre("SF")
                .price(100.0f)
                .stock(100L)
                .build();

        //genreRepository.save(genre);
        book = bookService.save(book);

        bookService.deleteById(book.getId());

        Assertions.assertEquals(0, bookService.findAll().size());

    }

    @Test
    void save() {
        Genre genre = Genre.builder().genre("SF").build();
        BookDTO book = BookDTO.builder()
                .title("T")
                .author("A")
                .genre("SF")
                .price(100.0f)
                .stock(100L)
                .build();

        genreRepository.save(genre);
        book = bookService.save(book);

        BookDTO bookDTO = bookService.findById(book.getId());
        Assertions.assertNotNull(bookDTO);
    }

    @Test
    void update() {
        Genre genre = Genre.builder().genre("SF").build();
        BookDTO book = BookDTO.builder()
                .title("T")
                .author("A")
                .genre("SF")
                .price(100.0f)
                .stock(100L)
                .build();

        genreRepository.save(genre);
        book = bookService.save(book);
        book.setAuthor("Updated");

        BookDTO update = bookService.update(book.getId(), book);

        Assertions.assertEquals(book.getAuthor(), update.getAuthor());

    }

    @Test
    void sell() {
        Genre genre = Genre.builder().genre("SF").build();
        BookDTO book = BookDTO.builder()
                .title("T")
                .author("A")
                .genre("SF")
                .price(100.0f)
                .stock(100L)
                .build();

        genreRepository.save(genre);
        book = bookService.save(book);

        Assertions.assertTrue(bookService.sell(book.getId()));
    }

    @Test
    void search(){
        Genre genre = Genre.builder().genre("SF").build();
        BookDTO book = BookDTO.builder()
                .title("T")
                .author("A")
                .genre("SF")
                .price(100.0f)
                .stock(100L)
                .build();
        bookService.save(book);

        BookDTO book1 = BookDTO.builder()
                .title("TT")
                .author("A")
                .genre("SF")
                .price(100.0f)
                .stock(100L)
                .build();
        bookService.save(book1);

        List<BookDTO> searched = bookService.search("T");
        Assertions.assertEquals(searched.size(), 1);

    }
}