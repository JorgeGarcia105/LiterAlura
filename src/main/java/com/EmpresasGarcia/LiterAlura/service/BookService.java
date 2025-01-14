package com.EmpresasGarcia.LiterAlura.service;

import com.EmpresasGarcia.LiterAlura.model.Book;
import com.EmpresasGarcia.LiterAlura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public String getData(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);

        }
        String json = response.body();
        return json;
    }

    // Método para listar todos los libros
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Método para buscar libros por idioma
    public List<Book> getBooksByLanguage(String language) {
        // Llama al método actualizado en el repositorio
        return bookRepository.findByLanguagesContaining(language);
    }

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public DoubleSummaryStatistics getDownloadStatistics() {
        return bookRepository.findAll().stream()
                .mapToDouble(Book::getDownloadCount)
                .summaryStatistics();
    }

    public List<Book> getTop10DownloadedBooks() {
        return bookRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(Book::getDownloadCount).reversed())
                .limit(10)
                .toList();
    }
}
