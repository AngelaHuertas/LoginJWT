package com.login.service;

import com.login.model.Recipe;
import com.login.model.UserSavedRecipe;
import com.login.repository.RecipeRepository;
import com.login.repository.UserSavedRecipeRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserSavedRecipeRepository userSavedRecipeRepository;

    public RecipeService(RecipeRepository recipeRepository, UserSavedRecipeRepository userSavedRecipeRepository) {
        this.recipeRepository = recipeRepository;
        this.userSavedRecipeRepository = userSavedRecipeRepository;
    }

    // ---- ADMIN ----
    public Mono<Recipe> createRecipe(Recipe recipe) {
        Recipe newRecipe = new Recipe(
                null,
                recipe.name(),
                recipe.description(),
                recipe.ingredients(),
                LocalDateTime.now()
        );
        return recipeRepository.save(newRecipe);
    }

    public Mono<Recipe> updateRecipe(Long id, Recipe recipe) {
        return recipeRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Receta no encontrada")))
                .flatMap(existing -> {
                    Recipe updated = new Recipe(
                            existing.id(),
                            recipe.name(),
                            recipe.description(),
                            recipe.ingredients(),
                            existing.createdAt()
                    );
                    return recipeRepository.save(updated);
                });
    }

    public Mono<Void> deleteRecipe(Long id) {
        return recipeRepository.deleteById(id);
    }

    // ---- USER/CLIENT ----
    public Mono<Object> saveRecipeForUser(Long userId, Long recipeId) {
        return userSavedRecipeRepository.findByUserIdAndRecipeId(userId, recipeId)
                .flatMap(existing -> Mono.error(new RuntimeException("Ya guardaste esta receta")))
                .switchIfEmpty(Mono.defer(() -> {
                    UserSavedRecipe relation = new UserSavedRecipe(
                            null,
                            userId,
                            recipeId,
                            LocalDateTime.now()
                    );
                    return userSavedRecipeRepository.save(relation)
                            .thenReturn("Receta guardada en favoritos");
                }));
    }

    public Mono<String> removeRecipeForUser(Long userId, Long recipeId) {
        return userSavedRecipeRepository.findByUserIdAndRecipeId(userId, recipeId)
                .switchIfEmpty(Mono.error(new RuntimeException("No has guardado esta receta")))
                .flatMap(saved -> userSavedRecipeRepository.delete(saved)
                        .thenReturn("Receta eliminada de favoritos"));
    }

    // ---- PÚBLICO ----
    public Flux<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }
}
