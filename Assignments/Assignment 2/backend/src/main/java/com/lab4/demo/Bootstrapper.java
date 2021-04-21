package com.lab4.demo;

import com.lab4.demo.bookstore.BookService;
import com.lab4.demo.bookstore.model.Genre;
import com.lab4.demo.bookstore.model.dto.BookDTO;
import com.lab4.demo.bookstore.repository.BookRepository;
import com.lab4.demo.bookstore.repository.GenreRepository;
import com.lab4.demo.security.AuthService;
import com.lab4.demo.security.dto.SignupRequest;
import com.lab4.demo.user.repository.RoleRepository;
import com.lab4.demo.user.repository.UserRepository;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final BookService bookService;

    private final BookRepository bookRepository;

    private final GenreRepository genreRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            userRepository.deleteAll();
            roleRepository.deleteAll();
            bookRepository.deleteAll();
            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }
            authService.register(SignupRequest.builder()
                    .email("iulia@email.com")
                    .username("iulia")
                    .password("iulia")
                    .roles(Set.of("ADMIN"))
                    .build());
            authService.register(SignupRequest.builder()
                    .email("iulia1@email.com")
                    .username("iulia1")
                    .password("iulia1")
                    .roles(Set.of("EMPLOYEE"))
                    .build());

            Genre genre = Genre.builder().genre("SF").build();
            BookDTO book = BookDTO.builder()
                    .title("Ender's Game")
                    .author("Orson Scott Card")
                    .genre("SF")
                    .price(25.0f)
                    .stock(100L)
                    .build();

            genreRepository.save(genre);
            book = bookService.save(book);
        }
    }
}
