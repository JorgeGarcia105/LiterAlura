package com.EmpresasGarcia.LiterAlura.service;

import com.EmpresasGarcia.LiterAlura.model.Author;
import com.EmpresasGarcia.LiterAlura.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> searchAuthorsByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Author> findAuthorsByBirthYearRange(int start, int end) {
        return authorRepository.findByBirthYearBetween(start, end);
    }

    public List<Author> findAuthorsByDeathYearRange(int start, int end) {
        return authorRepository.findByDeathYearBetween(start, end);
    }
}