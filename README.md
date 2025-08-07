# 📚 IronLibrary

Este proyecto es un sistema de gestión de bibliotecas desarrollado en Java. IronLibrary permite administrar libros, autores, estudiantes y préstamos mediante una interfaz de consola, siendo útil para entornos académicos.

## 🎯 Propósito y Alcance

IronLibrary proporciona una visión general del sistema de gestión bibliotecaria por línea de comandos. Cubre las capacidades clave, componentes principales y arquitectura para facilitar su comprensión y mantenimiento por parte de los desarrolladores.

El sistema se basa en un menú interactivo que permite realizar operaciones esenciales como registrar libros, emitir préstamos o buscar libros por título, autor o categoría.

---

## 🧩 Estructura del Proyecto

El sistema se organiza en los siguientes componentes:

### 📦 Clases Principales

- `Book`: Representa un libro con los atributos `isbn`, `title`, `category`, `quantity`.
- `Author`: Representa un autor asociado a un libro. Atributos: `name`, `email`, `bookIsbn`.
- `Student`: Representa un estudiante con `usn` (número de estudiante) y `name`.
- `Issue`: Representa un préstamo de libro. Atributos: `issueDate`, `returnDate`, `student`, `book`.

### 💾 Persistencia

- Repositorios como `BookRepository`, `IssueRepository`, etc., utilizan archivos CSV para almacenamiento.
- Se incluyen clases de prueba como `BookRepositoryTest`, `IssueRepositoryTest` usando **JUnit 5**.

---

## 🚀 Funcionalidades del Sistema

IronLibrary ofrece las siguientes opciones por consola:

| Opción del Menú           | Funcionalidad                                                | Entidades Clave               |
|---------------------------|--------------------------------------------------------------|-------------------------------|
| Agregar Libro con Autor   | Registrar nuevos libros y asociarlos a autores               | `Book`, `Author`              |
| Buscar Libros             | Buscar libros por título, categoría o nombre del autor       | `Book`, `Author`              |
| Listar Todos los Libros   | Mostrar catálogo completo con información del autor          | `Book`, `Author`              |
| Emitir Libros             | Prestar libros a estudiantes con fecha de devolución         | `Issue`, `Student`, `Book`    |
| Listar Libros por Estudiante | Ver libros emitidos a un estudiante específico             | `Issue`, `Student`            |

> El sistema aplica reglas de negocio como validación de correos electrónicos únicos, formato ISBN, control de inventario y cálculo automático de la fecha de devolución (7 días después del préstamo).

---

## ✅ Validaciones por Modelo

| Modelo   | Atributos Clave                             | Reglas de Validación                                                              |
|----------|---------------------------------------------|------------------------------------------------------------------------------------|
| `Book`   | `isbn`, `title`, `category`, `quantity`     | ISBN válido (10/13 dígitos), campos no vacíos, cantidad ≥ 0                       |
| `Author` | `name`, `email`, `bookIsbn`                 | Nombre no vacío, email válido y único                                             |
| `Student`| `usn`, `name`                               | USN y nombre no vacíos                                                            |
| `Issue`  | `issueDate`, `returnDate`, `student`, `book`| Fechas válidas, referencias no nulas a estudiante y libro                         |

---

## 🛠️ Tecnologías Utilizadas

- Java 17
- IntelliJ IDEA
- JUnit 5
- Git

---

## 📖 ¿Cómo Ejecutar?

1. Clona este repositorio:
   ```bash
   git clone https://github.com/iyasyr/IronLibrary.git
   
2. Abre el proyecto en IntelliJ, Eclipse u otro IDE compatible.

3. Ejecuta la clase Main ubicada en:
src/main/java/org.library/Main.java

4. Usa el menú interactivo para gestionar libros, estudiantes y préstamos.

## 🔗 Recursos Relacionados

🌐 [DeepWiki: Documentación Extendida](https://deepwiki.com/iyasyr/IronLibrary) – Consulta más detalles técnicos, ejemplos y descripciones ampliadas del sistema en DeepWiki.
