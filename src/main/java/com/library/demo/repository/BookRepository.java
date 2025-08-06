package com.library.demo.repository;

import com.library.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    List<Book> findByTitleIgnoreCase(String title);
    List<Book> findByCategoryIgnoreCase(String category);

    String title(String title);
}
