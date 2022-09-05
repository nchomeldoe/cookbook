package com.example.cookbook.repositories;

import com.example.cookbook.entities.Ingredient;
import com.example.cookbook.entities.Quantity;
import com.example.cookbook.entities.Recipe;
import com.example.cookbook.entities.RecipeElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeElementRepository extends JpaRepository<RecipeElement, Integer> {

    RecipeElement findRecipeElementByRecipeAndIngredientAndQuantity(Recipe recipe, Ingredient ingredient, Quantity quantity);

    RecipeElement findRecipeElementById(int id);

    void deleteRecipeElementByRecipe(Recipe recipe);
}
