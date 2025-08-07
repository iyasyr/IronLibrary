package org.library.model;

import java.util.regex.Pattern;

public class Author {
    private static int idCounter = 1;

    private int authorId;
    private String name;
    private String email;
    private Book authorBook;

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[\\w-.]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    // All-args constructor with validation
    public Author(String name, String email, Book authorBook) {
        this.authorId = idCounter++;
        setName(name);
        setEmail(email);
        setAuthorBook(authorBook);
    }

    public Author() {

    }

    // Getters
    public int getAuthorId() {
        return authorId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Book getAuthorBook() {
        return authorBook;
    }

    // Setters with validation
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be null or empty.");
        }
        this.name = name;
    }

    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        this.email = email;
    }

    public void setAuthorBook(Book authorBook) {
        if (authorBook == null) {
            throw new IllegalArgumentException("Author must be associated with a book.");
        }
        this.authorBook = authorBook;
    }

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
