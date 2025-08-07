package org.library.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.library.model.Author;
import org.library.model.Book;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AuthorRepositoryTest {

    @TempDir
    File tempDir;

    private File testFile;
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        testFile = new File(tempDir, "test_authors.csv");
        // Asegúrate de pasar la ruta del archivo temporal
        authorRepository = new AuthorRepository(testFile.getAbsolutePath());
    }

    @Test
    void testSaveAndFindAll() {
        Book book = new Book("123", "Test Book", "Fiction", 2);
        Author author = new Author("John Doe", "john@example.com", book);
        author.setAuthorId(1);

        // También setea el author en el libro si tienes bidirección
        book.setAuthor(author);

        authorRepository.save(author);

        List<Author> allAuthors = authorRepository.findAll();
        assertEquals(1, allAuthors.size());

        Author retrieved = allAuthors.get(0);
        assertEquals("John Doe", retrieved.getName());
        assertEquals("john@example.com", retrieved.getEmail());
        assertEquals("123", retrieved.getAuthorBook().getIsbn());
    }

    @Test
    void testFindByName() {
        Author author1 = new Author("Alice", "alice@mail.com", new Book("A1", "Alpha", "SciFi", 1));
        Author author2 = new Author("Bob", "bob@mail.com", new Book("B2", "Beta", "Drama", 2));
        author1.setAuthorId(1);
        author2.setAuthorId(2);

        authorRepository.save(author1);
        authorRepository.save(author2);

        List<Author> result = authorRepository.findByName("alice");
        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getName());
    }

    @Test
    void testFindByBookIsbn() {
        Book book = new Book("999", "Gamma", "Action", 3);
        Author author = new Author("Charlie", "charlie@mail.com", book);
        author.setAuthorId(1);
        book.setAuthor(author);

        authorRepository.save(author);

        Author found = authorRepository.findByBookIsbn("999");
        assertNotNull(found);
        assertEquals("Charlie", found.getName());
    }
}
