package com.EmpresasGarcia.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookDetails(
        @JsonAlias("title") String title,
        @JsonAlias("authors") List<AuthorDetails> authors,
        @JsonAlias("subjects") List<String> subjects,
        @JsonAlias("languages") List<String> languages,
        @JsonAlias("download_count") double downloadCount
) {}