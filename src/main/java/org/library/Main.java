package org.library;

import org.library.model.*;
import org.library.repository.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("IronLibrary");
        EntityManager em = emf.createEntityManager();

        BookRepository bookRepo = new BookRepository(em);
        AuthorRepository authorRepo = new AuthorRepository(em);
        StudentRepository studentRepo = new StudentRepository(em);
        IssueRepository issueRepo = new IssueRepository(em);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- IronLibrary Menu ---");
            System.out.println("1. Add a book");
            System.out.println("2. Search book by title");
            System.out.println("3. Search book by category");
            System.out.println("4. Search book by author");
            System.out.println("5. List all books with authors");
            System.out.println("6. Issue book to student");
            System.out.println("7. List books by student USN");
            System.out.println("8. Exit");

            System.out.print("Choose an option: ");
            int option = Integer.parseInt(scanner.nextLine());

            switch (option) {
                case 1 -> {
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    Book book = new Book(isbn, title, category, quantity);
                    bookRepo.save(book);

                    System.out.print("Enter author name: ");
                    String authorName = scanner.nextLine();
                    System.out.print("Enter author email: ");
                    String email = scanner.nextLine();

                    Author author = new Author(authorName, email, book);
                    authorRepo.save(author);

                    System.out.println("Book and author added.");
                }

                case 2 -> {
                    System.out.print("Enter title to search: ");
                    String title = scanner.nextLine();
                    List<Book> books = bookRepo.findByTitle(title);
                    books.forEach(System.out::println);
                }

                case 3 -> {
                    System.out.print("Enter category to search: ");
                    String category = scanner.nextLine();
                    List<Book> books = bookRepo.findByCategory(category);
                    books.forEach(System.out::println);
                }

                case 4 -> {
                    System.out.print("Enter author name: ");
                    String name = scanner.nextLine();
                    List<Author> authors = authorRepo.findByName(name);
                    authors.forEach(a -> {
                        System.out.println("Author: " + a.getName());
                        System.out.println("Book: " + a.getAuthorBook().getTitle());
                    });
                }

                case 5 -> {
                    List<Author> authors = authorRepo.findAll();
                    for (Author a : authors) {
                        System.out.println(a.getName() + " - " + a.getAuthorBook().getTitle());
                    }
                }

                case 6 -> {
                    System.out.print("Enter student USN: ");
                    String usn = scanner.nextLine();
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();

                    Student student = studentRepo.findByUsn(usn);
                    if (student == null) {
                        student = new Student(usn, name);
                        studentRepo.save(student);
                    }

                    System.out.print("Enter book ISBN to issue: ");
                    String isbn = scanner.nextLine();
                    Book book = bookRepo.findByIsbn(isbn);

                    if (book == null || book.getQuantity() <= 0) {
                        System.out.println("Book not available.");
                        break;
                    }

                    Issue issue = new Issue(LocalDate.now(), null, student, book);
                    issueRepo.save(issue);

                    // reduce quantity
                    book.setQuantity(book.getQuantity() - 1);
                    bookRepo.save(book);

                    System.out.println("Book issued to student.");
                }

                case 7 -> {
                    System.out.print("Enter student USN: ");
                    String usn = scanner.nextLine();
                    List<Issue> issues = issueRepo.findByStudentUsn(usn);
                    for (Issue i : issues) {
                        System.out.println(i.getIssueBook().getTitle());
                    }
                }

                case 8 -> {
                    em.close();
                    emf.close();
                    System.out.println("Exiting IronLibrary.");
                    return;
                }

                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }
}
