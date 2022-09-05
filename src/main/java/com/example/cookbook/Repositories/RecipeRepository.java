package com.example.cookbook.Repositories;

import com.example.cookbook.Entities.Ingredient;
import com.example.cookbook.Entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    Recipe findRecipeById(int id);


}
