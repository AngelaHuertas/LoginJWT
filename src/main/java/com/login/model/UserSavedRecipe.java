package com.login.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("user_saved_recipes")
public record UserSavedRecipe(
        @Id Long id,
        Long userId,
        Long recipeId,
        java.time.LocalDateTime savedAt
) {}