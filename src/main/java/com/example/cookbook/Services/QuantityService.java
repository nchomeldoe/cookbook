package com.example.cookbook.Services;

import com.example.cookbook.Entities.Ingredient;
import com.example.cookbook.Entities.Quantity;
import com.example.cookbook.Entities.Recipe;
import com.example.cookbook.Entities.RecipeElement;
import com.example.cookbook.IngredientUnit;
import com.example.cookbook.Repositories.QuantityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuantityService {

    @Autowired
    QuantityRepository quantityRepository;

    public List<Quantity> getAllQuantities(){
        List<Quantity> quantities = quantityRepository.findAll();
        return quantities;
    }

    public Quantity getQuantityByValueAndUnit(Double value, IngredientUnit unit) {
        Quantity quantity = quantityRepository.findQuantityByValueAndUnit(value, unit);
        return quantity;
    }

    public Quantity getQuantityByRecipeElement(RecipeElement recipeElement) {
        List<Quantity> quantities = quantityRepository.findAll();
        return quantities.stream().filter(quantity -> quantity.getRecipeElements().contains(recipeElement)).findFirst().get();
    }

    public Quantity createQuantity(Quantity quantity) {
        return quantityRepository.save(quantity);
    }
}
