package com.example.cookbook.entities;

import com.example.cookbook.IngredientUnit;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Quantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double value;

    private IngredientUnit unit;

    @OneToMany(mappedBy = "quantity",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private Set<RecipeElement> recipeElements;

    public Quantity() {
    }

    public Quantity(double value, IngredientUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public IngredientUnit getUnit() {
        return unit;
    }

    public void setUnit(IngredientUnit unit) {
        this.unit = unit;
    }

    public Set<RecipeElement> getRecipeElements() {
        return this.recipeElements;
    }

    public void addRecipeElement(RecipeElement recipeElement) {
        recipeElements.add(recipeElement);
    }

    public void removeRecipeElement(RecipeElement recipeElement) {
        recipeElements.remove(recipeElement);
    }
}
