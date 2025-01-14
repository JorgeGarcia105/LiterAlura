package com.EmpresasGarcia.LiterAlura.repository;

import com.EmpresasGarcia.LiterAlura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByTitleContainsIgnoreCase(String title);

    // Método para obtener todos los libros
    List<Book> findAll();

    List<Book> findTop5ByOrderByDownloadCountDesc();

    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.name LIKE %:authorName%")
    List<Book> findBooksByAuthorName(String authorName);

    @Query("SELECT b FROM Book b JOIN b.languages l WHERE l = :language")
    List<Book> findBooksByLanguage(String language);

    // Método para buscar libros por idioma
    List<Book> findByLanguagesContaining(String language);
}