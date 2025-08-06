package com.library.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "books")

public class Book {

    @Id
    private String isbn;
    private String title;
    private String category;
    private int quantity;
}