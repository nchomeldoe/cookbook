package com.example.cookbook.services;

import com.example.cookbook.DeserializedRecipe;
import com.example.cookbook.IngredientUnit;
import com.example.cookbook.entities.Ingredient;
import com.example.cookbook.entities.Quantity;
import com.example.cookbook.entities.Recipe;
import com.example.cookbook.entities.RecipeElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.*;

import static java.lang.Integer.parseInt;

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

    public Recipe convertDeserializedRecipeIntoRecipe(DeserializedRecipe deserializedRecipe) {
            Recipe newRecipe = recipeService.createRecipe(new Recipe(deserializedRecipe.getServes(), deserializedRecipe.getName(), deserializedRecipe.getDescription(), deserializedRecipe.getCreatedBy(), deserializedRecipe.getCuisine()));
            List<Map<String, Map<String, String>>> ingredientsAndQuantities = deserializedRecipe.getIngredientsAndQuantities();
            for (Map<String, Map<String, String>> element : ingredientsAndQuantities) {
                Ingredient newIngredient = convertIngredientsAndQuantitiesElementIntoIngredient(element);
                Quantity newQuantity = convertIngredientsAndQuantitiesElementIntoQuantity(element);
                recipeElementService.createRecipeElement(new RecipeElement(newIngredient, newRecipe, newQuantity));
            }
            return newRecipe;
    }

    public Ingredient convertIngredientsAndQuantitiesElementIntoIngredient(Map<String, Map<String, String>> ingredientsAndQuantitiesElement) {
        String ingredientName = ingredientsAndQuantitiesElement.get("ingredient").get("name");
        Ingredient newIngredient;
        Ingredient ingredientMatchingName = ingredientService.getIngredientByName(ingredientName);
        if (ingredientMatchingName == null) {
            newIngredient = ingredientService.createIngredient(new Ingredient(ingredientName));
        } else {
            newIngredient = ingredientMatchingName;
        }
        return newIngredient;
    }

    public Quantity convertIngredientsAndQuantitiesElementIntoQuantity(Map<String, Map<String, String>> ingredientsAndQuantitiesElement) {
        Map<String, String> quantity = ingredientsAndQuantitiesElement.get("quantity");
        Quantity newQuantity;
        Quantity quantityMatchingValueAndUnit = quantityService.getQuantityByValueAndUnit(Double.parseDouble(quantity.get("value")), IngredientUnit.valueOf(quantity.get("unit")));
        if (quantityMatchingValueAndUnit == null) {
            newQuantity = quantityService.createQuantity(new Quantity(Double.parseDouble(quantity.get("value")), IngredientUnit.valueOf(quantity.get("unit"))));
        } else {
            newQuantity = quantityMatchingValueAndUnit;
        }
        return newQuantity;
    }

    public Recipe updateExitingRecipeFromDeserializedRecipe(Recipe existingRecipe, DeserializedRecipe deserializedRecipe) {
        existingRecipe.setName(deserializedRecipe.getName());
        existingRecipe.setServes(deserializedRecipe.getServes());
        existingRecipe.setDescription(deserializedRecipe.getDescription());
        existingRecipe.setCreatedBy(deserializedRecipe.getCreatedBy());
        existingRecipe.setCuisine(deserializedRecipe.getCuisine());
        recipeElementService.deleteRecipeElementByRecipe(existingRecipe);
        List<Map<String, Map<String, String>>> ingredientsAndQuantities = deserializedRecipe.getIngredientsAndQuantities();
        for (Map<String, Map<String, String>> element : ingredientsAndQuantities) {
            Ingredient newIngredient = convertIngredientsAndQuantitiesElementIntoIngredient(element);
            Quantity newQuantity = convertIngredientsAndQuantitiesElementIntoQuantity(element);
            recipeElementService.createRecipeElement(new RecipeElement(newIngredient, existingRecipe, newQuantity));
        }
        return existingRecipe;
    }
}
