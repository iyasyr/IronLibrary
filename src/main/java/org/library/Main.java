package org.library;

import org.library.Service.LibraryService;
import org.library.model.Author;
import org.library.model.Book;
import org.library.model.Issue;
import org.library.util.InputValidator;
import org.library.util.IsbnUtil;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n📚 Library Menu");
            System.out.println("1. Add Book with Author");
            System.out.println("2. Search Book by Title");
            System.out.println("3. Search Book by Category");
            System.out.println("4. Search Book by Author");
            System.out.println("5. List All Books with Authors");
            System.out.println("6. Issue book to student");
            System.out.println("7. List books by usn");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    try {
                        String isbn;
                        while (true) {
                            System.out.print("ISBN: ");
                            isbn = scanner.nextLine();
                            try {
                                IsbnUtil.validate(isbn);  // throws if invalid
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("❌ " + e.getMessage());
                            }
                        }

                        String title;
                        while (true) {
                            System.out.print("Title: ");
                            title = scanner.nextLine();
                            try {
                                InputValidator.validateNonEmpty(title, "Title");
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("❌ " + e.getMessage());
                            }
                        }

                        String category;
                        while (true) {
                            System.out.print("Category: ");
                            category = scanner.nextLine();
                            try {
                                InputValidator.validateNonEmpty(category, "Category");
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("❌ " + e.getMessage());
                            }
                        }

                        int quantity;
                        while (true) {
                            System.out.print("Quantity: ");
                            String quantityInput = scanner.nextLine();
                            try {
                                InputValidator.validateQuantity(quantityInput);
                                quantity = Integer.parseInt(quantityInput);
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("❌ " + e.getMessage());
                            }
                        }

                        String authorName;
                        while (true) {
                            System.out.print("Author Name: ");
                            authorName = scanner.nextLine();
                            try {
                                InputValidator.validateNonEmpty(authorName, "Author Name");
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("❌ " + e.getMessage());
                            }
                        }

                        String authorEmail;
                        while (true) {
                            System.out.print("Author Email: ");
                            authorEmail = scanner.nextLine();
                            try {
                                InputValidator.validateEmail(authorEmail);
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println("❌ " + e.getMessage());
                            }
                        }

                        libraryService.addBookWithAuthor(isbn, title, category, quantity, authorName, authorEmail);
                        System.out.println("✅ Book and author added successfully!");

                    } catch (Exception e) {
                        System.out.println("❌ Error adding book: " + e.getMessage());
                    }
                }

                case 2 -> {
                    String title;
                    while (true) {
                        System.out.print("Enter book title to search: ");
                        title = scanner.nextLine();
                        try {
                            InputValidator.validateNonEmpty(title, "Title");
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("❌ " + e.getMessage());
                        }
                    }

                    List<Book> books = libraryService.searchBookByTitle(title);
                    if (books.isEmpty()) {
                        System.out.println("❌ No books found.");
                    } else {
                        System.out.printf("%-25s%-20s%-15s%s\n", "Book ISBN", "Book Title", "Category", "No of Books");
                        books.forEach(book -> System.out.printf("%-25s%-20s%-15s%s\n",
                                book.getIsbn(),
                                book.getTitle(),
                                book.getCategory(),
                                book.getQuantity()));
                    }
                }

                case 3 -> {
                    System.out.print("Enter category to search: ");
                    String category = scanner.nextLine().trim();

                    try {
                        InputValidator.validateNonEmpty(category, "Category");
                        List<Book> books = libraryService.searchBookByCategory(category);
                        if (books.isEmpty()) {
                            System.out.println("❌ No books found.");
                        } else {
                            System.out.printf("%-25s%-20s%-15s%s\n", "Book ISBN", "Book Title", "Category", "No of Books");
                            books.forEach(book -> System.out.printf("%-25s%-20s%-15s%s\n",
                                    book.getIsbn(),
                                    book.getTitle(),
                                    book.getCategory(),
                                    book.getQuantity()));
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                }

                case 4 -> {
                    System.out.print("Enter author name to search: ");
                    String authorName = scanner.nextLine();

                    try {
                        InputValidator.validateNonEmpty(authorName, "Author name");
                        List<Author> authors = libraryService.searchBookByAuthor(authorName);

                        if (authors.isEmpty()) {
                            System.out.println("❌ No books found.");
                        } else {
                            System.out.printf("%-25s%-20s%-15s%s\n", "Book ISBN", "Book Title", "Category", "No of Books");

                            for (Author author : authors) {
                                Book book = author.getAuthorBook();
                                if (book != null) {
                                    System.out.printf("%-25s%-20s%-15s%s\n",
                                            book.getIsbn(),
                                            book.getTitle(),
                                            book.getCategory(),
                                            book.getQuantity());
                                } else {
                                    System.out.printf("%-25s%-20s%-15s%s\n", "-", "-", "-", "-");
                                }
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                }

                case 5 -> {
                    List<Author> authors = libraryService.listAllBooksWithAuthors();

                    if (authors.isEmpty()) {
                        System.out.println("❌ No books or authors found.");
                    } else {
                        System.out.printf("%-20s %-25s %-15s %-10s %-20s %-30s\n",
                                "Book ISBN", "Book Title", "Category", "Quantity", "Author Name", "Author Email");

                        for (Author author : authors) {
                            Book book = author.getAuthorBook();

                            if (book != null) {
                                System.out.printf("%-20s %-25s %-15s %-10d %-20s %-30s\n",
                                        book.getIsbn(),
                                        book.getTitle(),
                                        book.getCategory(),
                                        book.getQuantity(),
                                        author.getName(),
                                        author.getEmail());
                            } else {
                                System.out.printf("%-20s %-25s %-15s %-10s %-20s %-30s\n",
                                        "-", "-", "-", "-", author.getName(), author.getEmail());
                            }
                        }
                    }
                }

                case 6 -> {
                    System.out.print("Student USN: ");
                    String usn = scanner.nextLine();

                    System.out.print("Student Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Book ISBN to issue: ");
                    String isbn = scanner.nextLine();

                    try {
                        InputValidator.validateNonEmpty(usn, "Student USN");
                        InputValidator.validateNonEmpty(name, "Student Name");
                        InputValidator.validateNonEmpty(isbn, "Book ISBN");

                        Issue issue = libraryService.issueBook(usn, name, isbn);

                        if (issue == null) {
                            System.out.println("❌ Book not available or does not exist.");
                        } else {
                            System.out.println("✅ Book issued successfully.");
                            System.out.printf("Book Title   : %s\n", issue.getIssueBook().getTitle());
                            System.out.printf("Student Name : %s\n", issue.getIssueStudent().getName());
                            System.out.printf("Return Date  : %s\n", issue.getReturnDate());
                        }

                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                }

                case 7 -> {
                    System.out.print("Enter USN: ");
                    String usn = scanner.nextLine();

                    try {
                        InputValidator.validateNonEmpty(usn, "USN");

                        List<Issue> issues = libraryService.listBooksByUsn(usn);

                        if (issues.isEmpty()) {
                            System.out.println("📭 No books issued to this student.");
                        } else {
                            System.out.println();
                            System.out.printf("%-20s %-20s %-25s\n", "Book Title", "Student Name", "Return Date");
                            for (Issue issue : issues) {
                                String bookTitle = issue.getIssueBook() != null ? issue.getIssueBook().getTitle() : "N/A";
                                String studentName = issue.getIssueStudent() != null ? issue.getIssueStudent().getName() : "N/A";
                                String returnDate = issue.getReturnDate() != null ? issue.getReturnDate() : "N/A";

                                System.out.printf("%-20s %-20s %-25s\n", bookTitle, studentName, returnDate);
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ " + e.getMessage());
                    }
                }

                case 0 -> {
                    System.out.println("👋 Exiting. Goodbye!");
                    return;
                }

                default -> System.out.println("❗ Invalid option. Try again.");
            }
        }
    }
}
