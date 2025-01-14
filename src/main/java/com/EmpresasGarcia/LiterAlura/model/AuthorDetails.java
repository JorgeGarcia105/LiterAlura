package com.EmpresasGarcia.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record AuthorDetails(
        @JsonAlias("name") String name,
        @JsonAlias("birth_year") Integer birthYear,
        @JsonAlias("death_year") Integer deathYear
) {}