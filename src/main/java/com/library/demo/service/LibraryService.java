package com.library.demo.service;

import com.library.demo.model.Author;
import com.library.demo.model.Book;
import com.library.demo.model.Issue;
import com.library.demo.model.Student;
import com.library.demo.repository.AuthorRepository;
import com.library.demo.repository.BookRepository;
import com.library.demo.repository.IssueRepository;
import com.library.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LibraryService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private IssueRepository issueRepository;

    // 1. Añadir libro y su author
    public void addBookWithAuthor(
            String isbn,
            String title,
            String category,
            int quantity,
            String authorName,
            String authorEmail){

        Book book = new Book(isbn,title,category,quantity);
        bookRepository.save(book);

        Author author = new Author();
        author.setName(authorName);
        author.setEmail(authorEmail);
        author.setAuthorBook(book);
        authorRepository.save(author);
    }

    //2 Buscar libro por titulo
    public List<Book> searchBookByTitle(String title) {
        return bookRepository.findByTitleIgnoreCase(title);
    }
    //3 Buscar libro por categoria
    public List<Book> searchBookByCategory(String category) {
        return bookRepository.findByCategoryIgnoreCase(category);
    }
    //4 Buscar libro por author
    public List<Author> searchBookByAuthor(String authorName) {
        return authorRepository.findByNameIgnoreCase(authorName);
    }
    //5 Lista de todos los libros con autores
    public List<Author> listAllBooksWithAuthors() {
        return authorRepository.findAll();
    }
    //6. Issue book a estudiante
    public String issueBook(String usn,String studentName, String isbn) {
        Book book = bookRepository.findById(isbn).orElse(null);
        if (book == null|| book.getQuantity()<=0) {
            return "Book not available";
        }
        Student student = new Student(usn,studentName);
        studentRepository.save(student);

        LocalDateTime issueDate = LocalDateTime.now();
        LocalDateTime returnDate = issueDate.plusDays(7);

        Issue issue =new Issue();
        issue.setIssueDate(issueDate.toString());
        issue.setReturnDate(returnDate.toString());
        issue.setIssueBook(book);
        issue.setIssueStudent(student);

        issueRepository.save(issue);

        //actualizar cantidad
        book.setQuantity(book.getQuantity()-1);
        bookRepository.save(book);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy");
        return "Book issued. Return date: " + returnDate.format(formatter);
    }
    //7 Lista de libros por USN
    public List <Issue> listBooksByUsn(String usn) {
        return issueRepository.findByIssueStudent_Usn(usn);
    }
}
