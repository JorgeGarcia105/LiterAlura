package com.EmpresasGarcia.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookSearchResponse {

    // Lista de detalles del libro
    private List<BookDetails> results;

    public List<BookDetails> getResults() {
        return results;
    }

    public void setResults(List<BookDetails> results) {
        this.results = results;
    }
}
