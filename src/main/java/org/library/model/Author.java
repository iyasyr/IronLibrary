package org.library.model;

import java.awt.print.Book;

public class Author {

    private int authorId;
    private String name;
    private String email;
    private Book authorBook;

    // Constructor vacío
    public Author() {
    }

    // Constructor con todos los campos
    public Author(int authorId, String name, String email, Book authorBook) {
        this.authorId = authorId;
        this.name = name;
        this.email = email;
        this.authorBook = authorBook;
    }

    // Getters y Setters

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Book getAuthorBook() {
        return authorBook;
    }

    public void setAuthorBook(Book authorBook) {
        this.authorBook = authorBook;
    }

    // toString()

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", authorBook=" + authorBook +
                '}';
    }
}
