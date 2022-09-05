package com.example.cookbook.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique=true)
    private String name;

    @OneToMany(mappedBy = "ingredient",
//            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private Set<RecipeElement> recipeElements;

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
