package com.lab4.demo.bookstore.controllers;

import com.lab4.demo.bookstore.model.dto.BookDTO;
import com.lab4.demo.bookstore.BookService;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.report.ReportType;
import com.lab4.demo.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;


@RequiredArgsConstructor
@RestController
@RequestMapping(BOOKS)
public class BookController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<BookDTO> findAll(){
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO createBook(@RequestBody BookDTO bookDTO){
        return bookService.save(bookDTO);
    }

    @PatchMapping(ENTITY)
    public BookDTO editBook(@RequestBody BookDTO bookDTO, @PathVariable Long id) {
        return bookService.update(id, bookDTO);
    }

    @DeleteMapping(ENTITY)
    public void deleteBook(@PathVariable Long id){
        bookService.deleteById(id);
    }

    //sell
    @PostMapping(SELL)
    public ResponseEntity<?> sell(@RequestBody BookDTO book){
        if(bookService.sell(book.getId()))
        {
            return ResponseEntity.ok(new MessageResponse("Success"));
        }
        else
        {
            return ResponseEntity.badRequest().body(new MessageResponse("Not enough copies"));
        }
    }

    //report
    @GetMapping(EXPORT_REPORT)
    public String export(@PathVariable ReportType type){
        return reportServiceFactory.getReportService(type).export();
    }

    //search
    /*@GetMapping(SEARCH)
    public List<BookDTO> search(@RequestParam String field){
        return bookService.search(field);
    }*/
}
