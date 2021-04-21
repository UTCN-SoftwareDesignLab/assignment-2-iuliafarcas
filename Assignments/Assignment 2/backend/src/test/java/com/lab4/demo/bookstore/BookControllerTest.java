package com.lab4.demo.bookstore;

import com.lab4.demo.BaseControllerTest;
import com.lab4.demo.TestCreationFactory;
import com.lab4.demo.bookstore.controllers.BookController;
import com.lab4.demo.bookstore.model.dto.BookDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.user.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookController bookController;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        bookController = new BookController(bookService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void findAll() throws Exception {
        List<BookDTO> bookDTOS = TestCreationFactory.listOf(BookDTO.class);
        when(bookService.findAll()).thenReturn(bookDTOS);

        ResultActions result = mockMvc.perform(get(BOOKS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTOS));
    }

    @Test
    void createBook() throws Exception {
        BookDTO bookDTO = BookDTO.builder()
                .title("T")
                .author("A")
                .genre("SF")
                .price(100.0f)
                .stock(100L)
                .build();
        when(bookService.save(bookDTO)).thenReturn(bookDTO);

        ResultActions result = performPostWithRequestBody(BOOKS, bookDTO);
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTO));
    }

    @Test
    void editBook() throws Exception {
        BookDTO bookDTO = BookDTO.builder()
                .id(1L)
                .title("T")
                .author("A")
                .genre("SF")
                .price(100.0f)
                .stock(100L)
                .build();
        when(bookService.update(bookDTO.getId(), bookDTO)).thenReturn(bookDTO);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(BOOKS+ENTITY, bookDTO, bookDTO.getId());
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonContentToBe(bookDTO));

    }

    @Test
    void deleteBook() throws Exception {
        BookDTO bookDTO = BookDTO.builder()
                .id(1L)
                .title("T")
                .author("A")
                .genre("SF")
                .price(100.0f)
                .stock(100L)
                .build();

        ResultActions result = performDeleteWIthPathVariable(BOOKS+ENTITY, bookDTO.getId());
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    void sell() throws Exception {
        BookDTO bookDTO = BookDTO.builder()
                .id(1L)
                .title("T")
                .author("A")
                .genre("SF")
                .price(100.0f)
                .stock(100L)
                .build();

        when(bookService.sell(bookDTO.getId())).thenReturn(true);
        ResultActions result = performPostWithRequestBody(BOOKS+SELL, bookDTO);
        result.andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());

    }
}