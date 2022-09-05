package com.example.cookbook.Repositories;

import com.example.cookbook.Entities.Ingredient;
import com.example.cookbook.Entities.Quantity;
import com.example.cookbook.Entities.Recipe;
import com.example.cookbook.Entities.RecipeElement;
import com.example.cookbook.IngredientUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeElementRepository extends JpaRepository<RecipeElement, Integer> {

    RecipeElement findRecipeElementByRecipeAndIngredientAndQuantity(Recipe recipe, Ingredient ingredient, Quantity quantity);

    RecipeElement findRecipeElementById(int id);

    void deleteRecipeElementByRecipe(Recipe recipe);
}
