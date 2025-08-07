package org.library.repository;

import org.library.model.Author;
import org.library.model.Book;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class AuthorRepositoryTest {

    private static final String DEFAULT_FILE_PATH = "authors.csv";
    private final String filePath;

    private final BookRepository bookRepository;

    // Constructor por defecto usa archivo de producción
    public AuthorRepository() {
        this(DEFAULT_FILE_PATH);
    }

    // Constructor personalizado (para pruebas o rutas distintas)
    public AuthorRepository(String filePath) {
        this.filePath = filePath;
        this.bookRepository = new BookRepository(); // usa el repositorio de libros para cargar info relacionada
    }

    // Guardar un autor
    public void save(Author author) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(toCsvLine(author));
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error saving author", e);
        }
    }

    // Obtener todos los autores
    public List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        Map<String, Book> bookMap = bookRepository.toMap();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                authors.add(fromCsvLine(line, bookMap));
            }
        } catch (FileNotFoundException e) {
            // archivo aún no existe
        } catch (IOException e) {
            throw new RuntimeException("Error reading authors", e);
        }

        return authors;
    }

    // Buscar por nombre (ignora mayúsculas/minúsculas)
    public List<Author> findByName(String name) {
        return findAll().stream()
                .filter(a -> a.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    // Buscar por ISBN del libro
    public Author findByBookIsbn(String isbn) {
        return findAll().stream()
                .filter(a -> a.getAuthorBook() != null &&
                        isbn.equalsIgnoreCase(a.getAuthorBook().getIsbn()))
                .findFirst()
                .orElse(null);
    }

    // Convertir a línea CSV
    private String toCsvLine(Author author) {
        return String.join(",",
                String.valueOf(author.getAuthorId()),
                escape(author.getName()),
                escape(author.getEmail()),
                escape(author.getAuthorBook() != null ? author.getAuthorBook().getIsbn() : "")
        );
    }

    // Convertir línea CSV a Author
    private Author fromCsvLine(String line, Map<String, Book> bookMap) {
        String[] parts = line.split(",", -1);
        int id = Integer.parseInt(parts[0]);
        String name = unescape(parts[1]);
        String email = unescape(parts[2]);
        String isbn = unescape(parts[3]);

        Book book = bookMap.getOrDefault(isbn, null);

        Author author = new Author(name, email, book);
        author.setAuthorId(id);
        return author;
    }

    // Crear mapa ISBN → Book
    public Map<Integer, Author> toMap() {
        return findAll().stream()
                .collect(Collectors.toMap(
                        Author::getAuthorId,
                        a -> a,
                        (existing, replacement) -> existing
                ));
    }

    // Escapar comas
    private String escape(String s) {
        return s == null ? "" : s.replace(",", "\\,");
    }

    // Des-escapar comas
    private String unescape(String s) {
        return s.replace("\\,", ",");
    }
}
