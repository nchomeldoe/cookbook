package com.example.cookbook.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
public class RecipeElement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "ingredient_id", referencedColumnName = "id")
//    private Ingredient ingredient;
//
//    @ManyToMany(mappedBy = "recipeElements", cascade = { CascadeType.ALL })
//    Set<Recipe> recipes;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
//    @JoinColumn(name = "ingredient_id", nullable = false)
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    @JsonBackReference
    private Ingredient ingredient;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
//    @JoinColumn(name = "recipe_id", nullable = false)
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    @JsonBackReference
    private Recipe recipe;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false, orphanRemoval = true)
//    @JoinColumn(name = "quantity_id", nullable = false)
    @ManyToOne
    @JoinColumn(name = "quantity_id")
    @JsonBackReference
    private Quantity quantity;

    public RecipeElement() {
    }

    public RecipeElement(Ingredient ingredient, Recipe recipe, Quantity quantity) {
        this.ingredient = ingredient;
        this.recipe = recipe;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }
}
