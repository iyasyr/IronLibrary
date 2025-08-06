package org.library.Service;

package com.library.demo.service;

import com.library.demo.model.Author;
import com.library.demo.model.Book;
import com.library.demo.model.Issue;
import com.library.demo.model.Student;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class LibraryService {

    // Repositorios en memoria
    private final List<Book> books = new ArrayList<>();
    private final List<Author> authors = new ArrayList<>();
    private final List<Student> students = new ArrayList<>();
    private final List<Issue> issues = new ArrayList<>();

    private int issueIdCounter = 1;

    // 1. Añadir libro y su autor
    public void addBookWithAuthor(
            String isbn,
            String title,
            String category,
            int quantity,
            String authorName,
            String authorEmail) {

        Book book = new Book(isbn, title, category, quantity);
        books.add(book);

        Author author = new Author(0, authorName, authorEmail, book);
        authors.add(author);
    }

    // 2. Buscar libro por título
    public List<Book> searchBookByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .collect(Collectors.toList());
    }

    // 3. Buscar libro por categoría
    public List<Book> searchBookByCategory(String category) {
        return books.stream()
                .filter(book -> book.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // 4. Buscar libro por autor
    public List<Author> searchBookByAuthor(String authorName) {
        return authors.stream()
                .filter(author -> author.getName().equalsIgnoreCase(authorName))
                .collect(Collectors.toList());
    }

    // 5. Lista de todos los libros con autores
    public List<Author> listAllBooksWithAuthors() {
        return new ArrayList<>(authors);
    }

    // 6. Emitir libro a estudiante
    public String issueBook(String usn, String studentName, String isbn) {
        Book book = books.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);

        if (book == null || book.getQuantity() <= 0) {
            return "Book not available";
        }

        // Verificar si el estudiante ya existe
        Student student = students.stream()
                .filter(s -> s.getUsn().equals(usn))
                .findFirst()
                .orElseGet(() -> {
                    Student newStudent = new Student(usn, studentName);
                    students.add(newStudent);
                    return newStudent;
                });

        LocalDateTime issueDate = LocalDateTime.now();
        LocalDateTime returnDate = issueDate.plusDays(7);

        Issue issue = new Issue();
        issue.setIssueId(issueIdCounter++);
        issue.setIssueDate(issueDate.toString());
        issue.setReturnDate(returnDate.toString());
        issue.setIssueStudent(student);
        issue.setIssueBook(book);

        issues.add(issue);

        // Actualizar cantidad del libro
        book.setQuantity(book.getQuantity() - 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy");
        return "Book issued. Return date: " + returnDate.format(formatter);
    }

    // 7. Lista de libros por USN
    public List<Issue> listBooksByUsn(String usn) {
        return issues.stream()
                .filter(issue -> issue.getIssueStudent().getUsn().equals(usn))
                .collect(Collectors.toList());
    }
}
