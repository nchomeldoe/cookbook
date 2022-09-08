package com.example.cookbook.services;

import com.example.cookbook.DeserializedRecipe;
import com.example.cookbook.entities.Recipe;
import com.example.cookbook.entities.RecipeElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataTransformer {

    @Autowired
    RecipeService recipeService;

    @Autowired
    IngredientService ingredientService;

    @Autowired
    RecipeElementService recipeElementService;

    @Autowired
    QuantityService quantityService;

    public DeserializedRecipe convertRecipeIntoDeserializedRecipe(Recipe recipe) {
            List<Map<String, Map<String, String>>> ingredientsAndQuantitiesList = new ArrayList<>();
            DeserializedRecipe recipeToReturn = new DeserializedRecipe(recipe.getId(), recipe.getServes(), recipe.getName(), recipe.getDescription(), recipe.getCreatedBy(), recipe.getCuisine(), ingredientsAndQuantitiesList);
            Set<RecipeElement> recipeElements = recipe.getRecipeElements();
            for (RecipeElement recipeElement : recipeElements) {
                String ingredientName = ingredientService.getIngredientByRecipeElement(recipeElement).getName();
                Map<String, String> ingredientNameMap = new HashMap<>();
                ingredientNameMap.put("name", ingredientName);
                Map<String, Map<String, String>> ingredientAndQuantityMap = new HashMap<>();
                ingredientAndQuantityMap.put("ingredient", ingredientNameMap);

                String quantityValue = String.valueOf(quantityService.getQuantityByRecipeElement(recipeElement).getValue());
                String quantityUnit = String.valueOf(quantityService.getQuantityByRecipeElement(recipeElement).getUnit());
                Map<String, String> valueAndUnitMap = new HashMap<>();
                valueAndUnitMap.put("value", quantityValue);
                valueAndUnitMap.put("unit", quantityUnit);
                ingredientAndQuantityMap.put("quantity", valueAndUnitMap);
                ingredientsAndQuantitiesList.add(ingredientAndQuantityMap);
            }
            recipeToReturn.setIngredientsAndQuantities(ingredientsAndQuantitiesList);
            return recipeToReturn;
        }
}
