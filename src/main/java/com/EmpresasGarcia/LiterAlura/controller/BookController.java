package com.EmpresasGarcia.LiterAlura.controller;

import com.EmpresasGarcia.LiterAlura.model.Book;
import com.EmpresasGarcia.LiterAlura.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    // Endpoint para listar libros registrados
    @GetMapping("/books")
    public List<Book> listAllBooks() {
        return bookService.getAllBooks();
    }

    // Endpoint para buscar libros por idioma
    @GetMapping("/books/language")
    public List<Book> listBooksByLanguage(@RequestParam String language) {
        return bookService.getBooksByLanguage(language);
    }

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/statistics")
    public DoubleSummaryStatistics getStatistics() {
        return bookService.getDownloadStatistics();
    }

    @GetMapping("/top10")
    public List<Book> getTop10Books() {
        return bookService.getTop10DownloadedBooks();
    }
}