package com.example.cookbook;

import com.example.cookbook.entities.Ingredient;
import com.example.cookbook.entities.Quantity;
import com.example.cookbook.entities.Recipe;
import com.example.cookbook.entities.RecipeElement;
import com.example.cookbook.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.*;

import static java.lang.Integer.parseInt;

@CrossOrigin
@RestController
public class CookbookController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    IngredientService ingredientService;

    @Autowired
    RecipeElementService recipeElementService;

    @Autowired
    QuantityService quantityService;
    @Autowired
    DataTransformer dataTransformer;

    @GetMapping("/cookbook/recipes")
    public ResponseEntity<?> getRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        List<DeserializedRecipe> recipesToReturn = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipesToReturn.add(dataTransformer.convertRecipeIntoDeserializedRecipe(recipe));
        }
        return ResponseEntity.status(HttpStatus.OK).body(recipesToReturn);
    }


    @GetMapping("/cookbook/recipe/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable String id) {
        Recipe recipe = recipeService.getRecipeById(parseInt(id));
        if(recipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find a recipe with that id");
        }
        DeserializedRecipe recipeToReturn = dataTransformer.convertRecipeIntoDeserializedRecipe(recipe);
        return ResponseEntity.status(HttpStatus.OK).body(recipe);
    }

    @GetMapping("/cookbook/recipe-elements")
    public ResponseEntity<List<RecipeElement>> getRecipeElements() {
        List<RecipeElement> recipeElements = recipeElementService.getAllRecipeElements();
        return ResponseEntity.status(HttpStatus.OK).body(recipeElements);
    }

    @GetMapping("/cookbook/ingredients")
    public ResponseEntity<List<Ingredient>> getIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.status(HttpStatus.OK).body(ingredients);
    }

    @GetMapping("/cookbook/quantities")
    public ResponseEntity<List<Quantity>> getQuantities() {
        List<Quantity> quantities = quantityService.getAllQuantities();
        return ResponseEntity.status(HttpStatus.OK).body(quantities);
    }

    @GetMapping("/cookbook/ingredient")
    public ResponseEntity<?> getIngredientByName(@RequestParam String name) {
        Ingredient ingredient = ingredientService.getIngredientByName(name);
        if(ingredient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find an ingredient with that name");
        }
        return ResponseEntity.status(HttpStatus.OK).body(ingredient);
    }

    @GetMapping("/cookbook/ingredient/recipe-element")
    public ResponseEntity<?> getIngredientByRecipeElement(@RequestParam String recipeElementId) {
        RecipeElement recipeElement = recipeElementService.getRecipeElementById(parseInt(recipeElementId));
        if(recipeElement == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find a recipe element with that id");
        }
        Ingredient ingredient = ingredientService.getIngredientByRecipeElement(recipeElement);
        if(ingredient == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find an ingredient with that name");
        }
        return ResponseEntity.status(HttpStatus.OK).body(ingredient);
    }


    @GetMapping("/cookbook/quantity")
    public ResponseEntity<?> getQuantityByValueAndUnit(@RequestParam String value, @RequestParam String unit) {
        Quantity quantity = quantityService.getQuantityByValueAndUnit(Double.parseDouble(value), IngredientUnit.valueOf(unit));
        if(quantity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find a matching quantity");
        }
        return ResponseEntity.status(HttpStatus.OK).body(quantity);
    }

    @GetMapping("/cookbook/quantity/recipe-element")
    public ResponseEntity<?> getQuantityByRecipeElement(@RequestParam String recipeElementId) {
        RecipeElement recipeElement = recipeElementService.getRecipeElementById(parseInt(recipeElementId));
        if(recipeElement == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find a recipe element with that id");
        }
        Quantity quantity = quantityService.getQuantityByRecipeElement(recipeElement);
        if(quantity == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find a matching quantity");
        }
        return ResponseEntity.status(HttpStatus.OK).body(quantity);
    }

    @PostMapping("/cookbook/recipe")
    public ResponseEntity<?> createRecipe(@RequestBody DeserializedRecipe recipe) {
        try {
            Recipe createdRecipe = dataTransformer.convertDeserializedRecipeIntoRecipe(recipe);
            return ResponseEntity.status(HttpStatus.OK).body(createdRecipe);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @Transactional
    @PutMapping("/cookbook/recipe/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable String id, @RequestBody DeserializedRecipe recipe) {
        Recipe existingRecipe = recipeService.getRecipeById(parseInt(id));
        if (existingRecipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find a recipe with that id");
        } else {
            try {
                existingRecipe = dataTransformer.updateExitingRecipeFromDeserializedRecipe(existingRecipe, recipe);
                return ResponseEntity.status(HttpStatus.OK).body(existingRecipe);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        }
    }

    @DeleteMapping("/cookbook/recipe/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String id) {
        boolean isDeleted = recipeService.deleteRecipeById(parseInt(id));
        if (isDeleted == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find a recipe to delete with that id");
        }
        return ResponseEntity.status(HttpStatus.OK).body("recipe deleted successfully");
    }


}
