package com.example.cookbook.services;

import com.example.cookbook.entities.Quantity;
import com.example.cookbook.entities.RecipeElement;
import com.example.cookbook.IngredientUnit;
import com.example.cookbook.repositories.QuantityRepository;
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
