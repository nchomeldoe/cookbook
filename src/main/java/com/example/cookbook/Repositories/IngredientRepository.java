package com.example.cookbook.Repositories;

import com.example.cookbook.Entities.Ingredient;
import com.example.cookbook.Entities.RecipeElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Ingredient findIngredientByName(String name);



}
