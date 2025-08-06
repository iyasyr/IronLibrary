package com.library.demo.repository;

import com.library.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

    // Encuentra todos los autores con ese nombre (ignora mayúsculas/minúsculas)
    List<Author> findByNameIgnoreCase(String name);

    // Encuentra un autor por el ISBN del libro que escribió
    Optional<Author> findByAuthorBook_Isbn(String isbn);
}