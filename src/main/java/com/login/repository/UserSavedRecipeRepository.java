package com.login.repository;

import com.login.model.UserSavedRecipe;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserSavedRecipeRepository extends R2dbcRepository<UserSavedRecipe, Long> {
    Mono<UserSavedRecipe> findByUserIdAndRecipeId(Long userId, Long recipeId);
}