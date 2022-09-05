package com.example.cookbook.repositories;

import com.example.cookbook.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Ingredient findIngredientByName(String name);



}
