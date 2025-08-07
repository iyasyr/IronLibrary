package org.library.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    @Test
    void testAuthorCreationWithValidData() {
        Book book = new Book("123-ABC", "Test Book", "Fiction", 3);
        Author author = new Author("Jane Doe", "jane@example.com", book);

        assertEquals("Jane Doe", author.getName());
        assertEquals("jane@example.com", author.getEmail());
        assertEquals(book, author.getAuthorBook());
        assertTrue(author.getAuthorId() > 0);
    }

    @Test
    void testSetAndGetFields() {
        Book book = new Book("456-DEF", "Another Book", "Mystery", 2);
        Author author = new Author();

        author.setAuthorId(10);
        author.setName("John Smith");
        author.setEmail("john@example.com");
        author.setAuthorBook(book);

        assertEquals(10, author.getAuthorId());
        assertEquals("John Smith", author.getName());
        assertEquals("john@example.com", author.getEmail());
        assertEquals(book, author.getAuthorBook());
    }

    @Test
    void testToStringFormat() {
        Book book = new Book("789-GHI", "Debugging Java", "Tech", 1);
        Author author = new Author("Alice", "alice@mail.com", book);
        String output = author.toString();

        assertTrue(output.contains("Alice"));
        assertTrue(output.contains("alice@mail.com"));
        assertTrue(output.contains("Debugging Java"));
    }
}
