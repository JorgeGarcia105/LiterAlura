package com.EmpresasGarcia.LiterAlura.repository;

import com.EmpresasGarcia.LiterAlura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE a.name LIKE %:name%")
    List<Author> findByNameContainingIgnoreCase(String name);

    @Query("SELECT a.name FROM Author a WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear >= :year)") // Cambié "anio" a "year"
    List<String> findLivingAuthorsInYear(int year);

    // Método para encontrar autores vivos en un año específico
    @Query("SELECT a FROM Author a WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear > :year)")
    List<Author> findAliveAuthorsInYear(@Param("year") int year);


    // Usamos @Query para escribir una consulta personalizada con JPQL
    @Query("SELECT a FROM Author a WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear > :year)")
    List<Author> findAuthorsAliveInYear(int year);
}