package org.library.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.library.model.Author;
import org.library.model.Book;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class AuthorRepositoryTest {

    @TempDir
    File tempDir;

    private File authorsFile;
    private File booksFile;
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        booksFile = new File(tempDir, "books.csv");
        authorsFile = new File(tempDir, "authors.csv");

        bookRepository = new BookRepository(booksFile.getAbsolutePath());
        authorRepository = new AuthorRepository(authorsFile.getAbsolutePath());
    }

    @Test
    void testSaveAndFindAll() {
        Book book = new Book("1234567890", "Test Book", "Fiction", 2);
        bookRepository.save(book);

        Author author = new Author("John Doe", "john@example.com", book);
        author.setAuthorId(1);

        Map<String, Book> bookMap = bookRepository.toMap();
        authorRepository.save(author, bookMap);

        List<Author> allAuthors = authorRepository.findAll(bookMap);
        assertEquals(1, allAuthors.size());

        Author retrieved = allAuthors.get(0);
        assertEquals("John Doe", retrieved.getName());
        assertEquals("john@example.com", retrieved.getEmail());
        assertNotNull(retrieved.getAuthorBook());
        assertEquals("1234567890", retrieved.getAuthorBook().getIsbn());
    }
}
