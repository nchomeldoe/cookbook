package com.example.cookbook.repositories;

import com.example.cookbook.entities.Quantity;
import com.example.cookbook.IngredientUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuantityRepository extends JpaRepository<Quantity, Integer> {
    Quantity findQuantityByValueAndUnit(Double value, IngredientUnit unit);
}
