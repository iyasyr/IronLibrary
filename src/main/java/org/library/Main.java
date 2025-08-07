package org.library;

import org.library.Service.LibraryService;
import org.library.model.Author;
import org.library.model.Book;

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
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> {
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Category: ");
                    String category = scanner.nextLine();
                    System.out.print("Quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());
                    System.out.print("Author Name: ");
                    String authorName = scanner.nextLine();
                    System.out.print("Author Email: ");
                    String authorEmail = scanner.nextLine();

                    libraryService.addBookWithAuthor(isbn, title, category, quantity, authorName, authorEmail);
                    System.out.println("✅ Book and author added successfully!");
                }

                case 2 -> {
                    System.out.print("Enter book title to search: ");
                    String title = scanner.nextLine();
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
                    String category = scanner.nextLine();
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
                }

                case 4 -> {
                    System.out.print("Enter author name to search: ");
                    String authorName = scanner.nextLine();
                    List<Author> authors = libraryService.searchBookByAuthor(authorName);
                    if (authors.isEmpty()) {
                        System.out.println("❌ No authors found.");
                    } else {
                        System.out.printf("%-25s%-20s%-15s%s\n", "Book ISBN", "Book Title", "Category", "No of Books");


                    }
                }

                case 5 -> {
                    List<Author> authors = libraryService.listAllBooksWithAuthors();
                    if (authors.isEmpty()) {
                        System.out.println("📭 No books or authors found.");
                    } else {
                        authors.forEach(System.out::println);
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
