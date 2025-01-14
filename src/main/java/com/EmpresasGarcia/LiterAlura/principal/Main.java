package com.EmpresasGarcia.LiterAlura.principal;

import com.EmpresasGarcia.LiterAlura.model.BookDetails;
import com.EmpresasGarcia.LiterAlura.model.Book;
import com.EmpresasGarcia.LiterAlura.model.BookSearchResponse;
import com.EmpresasGarcia.LiterAlura.repository.BookRepository;
import com.EmpresasGarcia.LiterAlura.repository.AuthorRepository;
import com.EmpresasGarcia.LiterAlura.service.BookService;
import com.EmpresasGarcia.LiterAlura.service.DataConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookService bookService;

    private Scanner scanner = new Scanner(System.in);
    private final String BASE_URL = "https://gutendex.com/books/";
    private DataConverter dataConverter = new DataConverter();

    @Override
    public void run(String... args) throws Exception {
        showMenu();
    }

    public void showMenu() {
        int option = -1;
        while (option != 0) {
            try {
                var menu = """
                        -----------------------------------
                        Choose an option by number:
                        1- Search book by title
                        2- List registered books
                        3- List registered authors
                        4- List authors alive in a specific year
                        5- List books by language
                        0 - Exit
                        -----------------------------------
                        """;
                System.out.println(menu);
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        searchBookByTitle();
                        break;
                    case 2:
                        listRegisteredBooks();
                        break;
                    case 3:
                        listRegisteredAuthors();
                        break;
                    case 4:
                        listAliveAuthors();
                        break;
                    case 5:
                        listBooksByLanguage();
                        break;
                    case 0:
                        System.out.println("Closing the application...");
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private void searchBookByTitle() {
        while (true) {
            System.out.println("Enter the title of the book you want to search for (or 0 to return to the main menu):");
            var bookTitle = scanner.nextLine();

            if (bookTitle.equals("0")) {
                break;
            }

            if (bookRepository.findByTitleContainsIgnoreCase(bookTitle).isPresent()) {
                System.out.println("The book is already registered in the database.");
                continue;
            }

            var json = bookService.getData(BASE_URL + "?search=" + bookTitle.replace(" ", "+"));

            // Cambiar BookDetails a BookSearchResponse
            var searchData = dataConverter.convertData(json, BookSearchResponse.class);

            if (searchData.getResults().isEmpty()) {
                System.out.println("Book not found.");
                continue;
            }

            System.out.println("-----------------------------------");
            System.out.println("Found books:");
            for (int i = 0; i < Math.min(10, searchData.getResults().size()); i++) {
                System.out.println((i + 1) + ". " + searchData.getResults().get(i).title());
            }
            System.out.println("Select the number of the book you want to save (or 0 to cancel):");
            int selection = scanner.nextInt();
            scanner.nextLine();

            if (selection == 0) {
                continue;
            }

            if (selection > 0 && selection <= searchData.getResults().size()) {
                BookDetails selectedBook = searchData.getResults().get(selection - 1);
                Book book = new Book(selectedBook);

                if (book.getLanguages() == null || book.getLanguages().isEmpty()) {
                    System.out.println("No languages found for the book.");
                } else {
                    printBookDetails(book);
                    bookRepository.save(book);
                    System.out.println("Book saved in the database.");
                }
            } else {
                System.out.println("Invalid selection.");
            }
        }
    }

    private void printBookDetails(Book book) {
        System.out.println("------------BOOK-----------------");
        System.out.println("Title: " + book.getTitle());
        book.getAuthors().forEach(author -> System.out.println("Author: " + author.getName()));
        System.out.println("Language: " + String.join(", ", book.getLanguages()));
        System.out.println("Download count: " + book.getDownloadCount());
        System.out.println("----------------------------------");
    }

    private void listRegisteredBooks() {
        System.out.println("Registered books:");
        bookRepository.findAll().forEach(book -> printBookDetails(book));
    }

    private void listRegisteredAuthors() {
        System.out.println("Registered authors:");
        authorRepository.findAll().stream()
                .distinct()
                .forEach(author -> System.out.println("Author: " + author.getName()));
    }

    private void listAliveAuthors() {
        System.out.println("Enter the year to list alive authors:");
        try {
            var year = scanner.nextInt();
            scanner.nextLine();

            var aliveAuthors = authorRepository.findAliveAuthorsInYear(year);
            if (aliveAuthors.isEmpty()) {
                System.out.println("No authors found alive in the year " + year);
            } else {
                aliveAuthors.forEach(author -> System.out.println("Author: " + author));
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
        }
    }

    private void listBooksByLanguage() {
        System.out.println("Enter the language to list books (ES, EN, FR, PT):");
        var language = scanner.nextLine().toLowerCase();

        var booksByLanguage = bookRepository.findBooksByLanguage(language);
        if (booksByLanguage.isEmpty()) {
            System.out.println("No books found in the language " + language);
        } else {
            booksByLanguage.forEach(book -> printBookDetails(book));
        }
    }
}
