package com.lab4.demo.bookstore;

import com.lab4.demo.bookstore.mapper.BookMapper;
import com.lab4.demo.bookstore.mapper.GenreMapper;
import com.lab4.demo.bookstore.model.Book;
import com.lab4.demo.bookstore.model.Genre;
import com.lab4.demo.bookstore.model.dto.BookDTO;
import com.lab4.demo.bookstore.repository.BookRepository;
import com.lab4.demo.bookstore.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public List<BookDTO> findAll(){
        return bookRepository.findAll().stream()
                .map(bookMapper::bookToDTO)
                .collect(Collectors.toList());
    }

    public BookDTO findById(Long id){
        return bookRepository.findById(id).map(bookMapper::bookToDTO).orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public void deleteById(Long id){
        bookRepository.deleteById(id);
    }

    public BookDTO save(BookDTO bookDTO){
        Book book = bookMapper.bookFromDTO(bookDTO);
        //Genre genre = genreRepository.findByGenre(bookDTO.getGenre())
          //      .orElseThrow(() -> new RuntimeException("Cannot find genre: " + bookDTO.getGenre()));

        //book.setGenre(genre);
        return bookMapper.bookToDTO(bookRepository.save(book));
    }

    public BookDTO update(Long id, BookDTO bookDTO){
        Book book = bookMapper.bookFromDTO(bookDTO);
        //Genre genre = genreRepository.findByGenre(bookDTO.getGenre())
          //      .orElseThrow(() -> new RuntimeException("Cannot find genre: " + bookDTO.getGenre()));

        book.setId(id);
        //book.setGenre(genre);
        return bookMapper.bookToDTO(bookRepository.save(book));
    }

    public boolean sell(Long id){
        //Book book = bookMapper.bookFromDTO(findById(id));
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
        if (book.getStock() <= 0 )
        {
            return false;
        }
        else
        {
            book.setStock(book.getStock() - 1);
        }
        bookRepository.save(book);
        return true;
    }

    public List<BookDTO> outOfStock(){
        List<BookDTO> books = bookRepository.findAll().stream().filter(book -> book.getStock() == 0)
                .map(bookMapper::bookToDTO)
                .collect(Collectors.toList());

        return books;
    }

    public List<BookDTO> search(String key){
        List<BookDTO> books = bookRepository.findBookByAuthorOrTitleOrGenre(key,key,key).stream()
                .map(bookMapper::bookToDTO)
                .collect(Collectors.toList());
        return books;
    }
}
