# 📚 IronLibrary

**IronLibrary** es un sistema de gestión de biblioteca desarrollado en **Java**, diseñado para facilitar la administración de libros y préstamos a estudiantes dentro de un entorno académico.

## 🧩 Arquitectura del Proyecto

El sistema se organiza en las siguientes clases y paquetes:

- `Book`: Representa un libro con ISBN, título, categoría y cantidad disponible.
- `Author`: Representa al autor de un libro (relación uno a uno).
- `Student`: Representa a un estudiante con su USN (número de estudiante) y nombre.
- `Issue`: Registra un préstamo de libro, incluyendo fechas y referencias al estudiante y libro involucrados.
- **Repositorios**: Clases como `BookRepository`, `IssueRepository`, etc., se encargan de la persistencia de datos en archivos CSV.
- **Pruebas**: Tests unitarios realizados con JUnit 5 para asegurar la correcta funcionalidad del sistema.

## 🚀 Funcionalidades principales

El sistema permite:

1. 📚 Agregar libros (junto a su autor)
2. 🔍 Buscar libros por título
3. 🔍 Buscar libros por categoría
4. 🔍 Buscar libros por autor
5. 📋 Listar todos los libros con sus autores
6. 🧾 Emitir un libro a un estudiante
7. 📖 Listar libros emitidos por USN del estudiante
8. ❌ Salir del sistema

## 🛠️ Tecnologías utilizadas

- Java 17
- IntelliJ IDEA
- JUnit 5
- Git

## 📁 Estructura del Proyecto

```
src/
├── main/
│ └── java/
│ └── org/library/
│ ├── model/
│ │ ├── Author.java
│ │ ├── Book.java
│ │ ├── Issue.java
│ │ └── Student.java
│ ├── repository/
│ │ ├── AuthorRepository.java
│ │ ├── BookRepository.java
│ │ ├── IssueRepository.java
│ │ └── StudentRepository.java
│ ├── service/
│ │ └── LibraryService.java
│ ├── util/
│ │ ├── InputValidator.java
│ │ └── IsbnUtil.java
│ └── Main.java
├── test/
│ └── java/
│ └── org/library/
│ ├── model/
│ │ ├── AuthorTest.java
│ │ ├── BookTest.java
│ │ ├── IssueTest.java
│ │ └── StudentTest.java
│ └── repository/
│ ├── AuthorRepositoryTest.java
│ ├── BookRepositoryTest.java
│ ├── IssueRepositoryTest.java
│ └── StudentRepositoryTest.java
├── .gitignore
└── pom.xml
```

---

## 📖 ¿Cómo ejecutar?

1. Clona el repositorio:
   ```bash
   git clone https://github.com/iyasyr/IronLibrary.git
   ```
2. Abre el proyecto en IntelliJ, Eclipse o tu IDE preferido.
3. Ejecuta la clase `Main` en `src/main/java/org.library`.
4. Usa el menú por consola para agregar libros, buscar por autor o categoría, emitir préstamos a estudiantes o salir del sistema.

---