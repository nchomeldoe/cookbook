package com.example.cookbook;

import com.example.cookbook.entities.Ingredient;
import com.example.cookbook.entities.Quantity;
import com.example.cookbook.entities.Recipe;
import com.example.cookbook.entities.RecipeElement;
import com.example.cookbook.services.IngredientService;
import com.example.cookbook.services.QuantityService;
import com.example.cookbook.services.RecipeElementService;
import com.example.cookbook.services.RecipeService;
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


    @GetMapping("/cookbook/recipes")
    public ResponseEntity<?> getRecipes() {
        List<Recipe> recipes = recipeService.getAllRecipes();
        List<DeserializedRecipe> recipesToReturn = new ArrayList<>();
        for (Recipe recipe : recipes) {
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
            recipesToReturn.add(recipeToReturn);
        }
        return ResponseEntity.status(HttpStatus.OK).body(recipesToReturn);
    }

    @GetMapping("/cookbook/recipe/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable String id) {
        Recipe recipe = recipeService.getRecipeById(parseInt(id));
        if(recipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find a recipe with that id");
        }
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
            Recipe newRecipe = new Recipe(recipe.getServes(), recipe.getName(), recipe.getDescription(), recipe.getCreatedBy(), recipe.getCuisine());
            Recipe createdRecipe = recipeService.createRecipe(newRecipe);
            List<Map<String, Map<String, String>>> ingredientsAndQuantities = recipe.getIngredientsAndQuantities();
            for (Map<String, Map<String, String>> element : ingredientsAndQuantities) {
                String ingredientName = element.get("ingredient").get("name");
                Ingredient newIngredient;
                Ingredient ingredientMatchingName = ingredientService.getIngredientByName(ingredientName);
                if(ingredientMatchingName == null ) {
                    newIngredient = ingredientService.createIngredient(new Ingredient(ingredientName));
                } else {
                    newIngredient = ingredientMatchingName;
                }
                Map<String, String> quantity = element.get("quantity");
                Quantity newQuantity;
                Quantity quantityMatchingValueAndUnit = quantityService.getQuantityByValueAndUnit(Double.parseDouble(quantity.get("value")), IngredientUnit.valueOf(quantity.get("unit")));
                if(quantityMatchingValueAndUnit == null) {
                    newQuantity = quantityService.createQuantity(new Quantity(Double.parseDouble(quantity.get("value")), IngredientUnit.valueOf(quantity.get("unit"))));
                } else {
                    newQuantity = quantityMatchingValueAndUnit;
                }
                recipeElementService.createRecipeElement(new RecipeElement(newIngredient, createdRecipe, newQuantity));
            }
            return ResponseEntity.status(HttpStatus.OK).body(createdRecipe);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Transactional
    @PutMapping("/cookbook/recipe/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable String id, @RequestBody DeserializedRecipe recipe) {
        Recipe existingRecipe = recipeService.getRecipeById(parseInt(id));
        if(existingRecipe == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find a recipe with that id");
        } else {
            try {
                existingRecipe.setName(recipe.getName());
                existingRecipe.setServes(recipe.getServes());
                existingRecipe.setDescription(recipe.getDescription());
                existingRecipe.setCreatedBy(recipe.getCreatedBy());
                existingRecipe.setCuisine(recipe.getCuisine());
                recipeElementService.deleteRecipeElementByRecipe(existingRecipe);
                List<Map<String, Map<String, String>>> ingredientsAndQuantities = recipe.getIngredientsAndQuantities();
                for (Map<String, Map<String, String>> element : ingredientsAndQuantities) {
                    String ingredientName = element.get("ingredient").get("name");
                    Ingredient newIngredient;
                    Ingredient ingredientMatchingName = ingredientService.getIngredientByName(ingredientName);
                    if(ingredientMatchingName == null ) {
                        newIngredient = ingredientService.createIngredient(new Ingredient(ingredientName));
                    } else {
                        newIngredient = ingredientMatchingName;
                    }
                    Map<String, String> quantity = element.get("quantity");
                    Quantity newQuantity;
                    Quantity quantityMatchingValueAndUnit = quantityService.getQuantityByValueAndUnit(Double.parseDouble(quantity.get("value")), IngredientUnit.valueOf(quantity.get("unit")));
                    if(quantityMatchingValueAndUnit == null) {
                        newQuantity = quantityService.createQuantity(new Quantity(Double.parseDouble(quantity.get("value")), IngredientUnit.valueOf(quantity.get("unit"))));
                    } else {
                        newQuantity = quantityMatchingValueAndUnit;
                    }
                    recipeElementService.createRecipeElement(new RecipeElement(newIngredient, existingRecipe, newQuantity));
                }
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

    @DeleteMapping("/cookbook/ingredient/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable String id) {
        boolean isDeleted = ingredientService.deleteIngredientById(parseInt(id));
        if (isDeleted == false) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("couldn't find an ingredient to delete with that id");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ingredient deleted successfully");
    }


}
