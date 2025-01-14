package com.EmpresasGarcia.LiterAlura.controller;

import com.EmpresasGarcia.LiterAlura.model.Author;
import com.EmpresasGarcia.LiterAlura.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/search")
    public List<Author> searchAuthors(@RequestParam String name) {
        return authorService.searchAuthorsByName(name);
    }

    @GetMapping("/by-birth-year")
    public List<Author> getAuthorsByBirthYearRange(@RequestParam int start, @RequestParam int end) {
        return authorService.findAuthorsByBirthYearRange(start, end);
    }

    @GetMapping("/by-death-year")
    public List<Author> getAuthorsByDeathYearRange(@RequestParam int start, @RequestParam int end) {
        return authorService.findAuthorsByDeathYearRange(start, end);
    }

}