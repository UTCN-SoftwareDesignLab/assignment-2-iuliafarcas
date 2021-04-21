package com.lab4.demo.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String title;


    @Column(nullable = false)
    private String author;

    //@ManyToOne
    //@JoinColumn(name = "genre_id",nullable = false)
    //private Genre genre;
    /*Am vrut sa fac tabel separat pt gen, insa nu am reusit sa fac si controller ca sa pot adauga
      si din frontend, asa ca l-am lasat string pana la urma.
    */
    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private Float price;

    @Column(nullable = false)
    private Long stock;
}
