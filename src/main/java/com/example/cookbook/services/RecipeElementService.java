package com.example.cookbook.services;

import com.example.cookbook.entities.Ingredient;
import com.example.cookbook.entities.Quantity;
import com.example.cookbook.entities.Recipe;
import com.example.cookbook.entities.RecipeElement;
import com.example.cookbook.repositories.RecipeElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeElementService {

    @Autowired
    RecipeElementRepository recipeElementRepository;

    public List<RecipeElement> getAllRecipeElements(){
        List<RecipeElement> recipeElements = recipeElementRepository.findAll();
        return recipeElements;
    }

    public RecipeElement getRecipeElementById(int id){
        RecipeElement recipeElement = recipeElementRepository.findRecipeElementById(id);
        return recipeElement;
    }

    public RecipeElement getRecipeElementByRecipeAndIngredientAndQuantity(Recipe recipe, Ingredient ingredient, Quantity quantity){
        RecipeElement recipeElement = recipeElementRepository.findRecipeElementByRecipeAndIngredientAndQuantity(recipe, ingredient, quantity);
        return recipeElement;
    }

    public RecipeElement createRecipeElement(RecipeElement recipeElement) {
        return recipeElementRepository.save(recipeElement);
    }

    public void deleteRecipeElementByRecipe(Recipe recipe) {
        recipeElementRepository.deleteRecipeElementByRecipe(recipe);
    }

}
