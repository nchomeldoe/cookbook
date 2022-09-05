package com.example.cookbook.Services;

import com.example.cookbook.Entities.Ingredient;
import com.example.cookbook.Entities.Recipe;
import com.example.cookbook.Entities.RecipeElement;
import com.example.cookbook.Repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {


    @Autowired
    IngredientRepository ingredientRepository;

    public List<Ingredient> getAllIngredients(){
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients;
    }

    public Ingredient getIngredientByName(String name) {
       return ingredientRepository.findIngredientByName(name);
    }

    public Ingredient getIngredientByRecipeElement(RecipeElement recipeElement) {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return ingredients.stream().filter(ingredient -> ingredient.getRecipeElements().contains(recipeElement)).findFirst().get();
    }

    public Ingredient createIngredient(Ingredient ingredient) {
//        if (ingredient.getName() == null || ingredient.getName().length() < 1) {
//            throw new RuntimeException("Ingredient must have text");
//        }

        return ingredientRepository.save(ingredient);
    }

}
