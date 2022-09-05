package com.example.cookbook.services;

import com.example.cookbook.entities.Recipe;
import com.example.cookbook.repositories.RecipeElementRepository;
import com.example.cookbook.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {


    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeElementRepository recipeElementRepository;

    public List<Recipe> getAllRecipes(){
        List<Recipe> recipes = recipeRepository.findAll();
        return recipes;
    }

    public Recipe getRecipeById(int id){
        Recipe recipe = recipeRepository.findRecipeById(id);
        return recipe;
    }

    public Recipe createRecipe(Recipe recipe) {
        Recipe savedRecipe = recipeRepository.save(recipe);
        return savedRecipe;
    }

    public boolean deleteRecipeById(int id){
        Recipe recipeToDelete = recipeRepository.findById(id).orElse(null);
        if(recipeToDelete == null){
            return false;
        }
        recipeRepository.delete(recipeToDelete);
        return true;
    }


}
