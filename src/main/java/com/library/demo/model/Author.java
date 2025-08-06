package com.library.demo.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int authorId;
    private  String name;
    private  String email;
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "book_isbn", referencedColumnName = "isbn")
    private Book authorBook;


}