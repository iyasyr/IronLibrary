package org.library.model;

import org.junit.jupiter.api.*;
import org.library.repository.AuthorRepository;
import org.library.repository.BookRepository;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    private static final String AUTHORS_CSV = "authors.csv";
    private static final String BOOKS_CSV = "books.csv";

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    @BeforeEach
    void setup() {
        // Limpia los archivos antes de cada prueba
        new File(AUTHORS_CSV).delete();
        new File(BOOKS_CSV).delete();

        authorRepository = new AuthorRepository();
        bookRepository = new BookRepository();
    }

    @Test
    void testSaveAndFindAll() {
        Book book = new Book("123-A", "Clean Code", "Programming", 5);
        bookRepository.save(book);

        Author author = new Author(1, "Robert C. Martin", "unclebob@mail.com", book);
        authorRepository.save(author);

        List<Author> authors = authorRepository.findAll();
        assertEquals(1, authors.size());
        assertEquals("Robert C. Martin", authors.get(0).getName());
    }

    @Test
    void testFindByNameIgnoreCase() {
        Book book = new Book("456-B", "Refactoring", "Programming", 3);
        bookRepository.save(book);

        Author author = new Author(2, "Martin Fowler", "mfowler@mail.com", book);
        authorRepository.save(author);

        List<Author> result = authorRepository.findByName("martin fowler");
        assertEquals(1, result.size());
        assertEquals("Martin Fowler", result.get(0).getName());
    }

    @Test
    void testFindByBookIsbn() {
        Book book = new Book("789-C", "The Pragmatic Programmer", "Programming", 4);
        bookRepository.save(book);

        Author author = new Author(3, "Andy Hunt", "andy@mail.com", book);
        authorRepository.save(author);

        Author found = authorRepository.findByBookIsbn("789-C");
        assertNotNull(found);
        assertEquals("Andy Hunt", found.getName());
    }
}
