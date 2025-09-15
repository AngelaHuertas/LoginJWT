package com.login.controller;

import com.login.model.Recipe;
import com.login.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // ---- ENDPOINTS ADMIN ----
    @PostMapping("/admin/recipes")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Recipe> createRecipe(@Valid @RequestBody Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }

    @PutMapping("/admin/recipes/{id}")
    public Mono<Recipe> updateRecipe(@PathVariable Long id, @Valid @RequestBody Recipe recipe) {
        return recipeService.updateRecipe(id, recipe);
    }

    @DeleteMapping("/admin/recipes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteRecipe(@PathVariable Long id) {
        return recipeService.deleteRecipe(id);
    }

    // ---- ENDPOINTS USER/CLIENT ----
    @PostMapping("/user/{userId}/recipes/{recipeId}/save")
    public Mono<Object> saveRecipe(@PathVariable Long userId, @PathVariable Long recipeId) {
        return recipeService.saveRecipeForUser(userId, recipeId);
    }

    @DeleteMapping("/user/{userId}/recipes/{recipeId}/remove")
    public Mono<String> removeRecipe(@PathVariable Long userId, @PathVariable Long recipeId) {
        return recipeService.removeRecipeForUser(userId, recipeId);
    }

    // ---- ENDPOINTS PUBLICOS ----
    @GetMapping("/recipes")
    public Flux<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }
}
