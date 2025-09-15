package com.login.repository;

import com.login.model.Recipe;

import reactor.core.publisher.Flux;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface RecipeRepository extends R2dbcRepository<Recipe, Long> {
    Flux<Recipe> findByNameContainingIgnoreCase(String name);
}

