package com.example.cookbook.Repositories;

import com.example.cookbook.Entities.Ingredient;
import com.example.cookbook.Entities.Quantity;
import com.example.cookbook.IngredientUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityRepository extends JpaRepository<Quantity, Integer> {
    Quantity findQuantityByValueAndUnit(Double value, IngredientUnit unit);
}
