package com.example.cookbook.services;

import com.example.cookbook.entities.Ingredient;
import com.example.cookbook.entities.Recipe;
import com.example.cookbook.entities.RecipeElement;
import com.example.cookbook.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        return ingredientRepository.save(ingredient);
    }

    public boolean deleteIngredientById(int id){
        Ingredient ingredientToDelete = ingredientRepository.findById(id).orElse(null);
        if(ingredientToDelete == null){
            return false;
        }
        ingredientRepository.delete(ingredientToDelete);
        return true;
    }

}
