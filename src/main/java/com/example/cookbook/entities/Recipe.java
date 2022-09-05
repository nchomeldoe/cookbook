package com.example.cookbook.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String createdBy;
    private String cuisine;
    private Timestamp dateCreated;

    @OneToMany(mappedBy = "recipe",
//            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private Set<RecipeElement> recipeElements;

    public Recipe() {
        this.dateCreated = new Timestamp(new Date().getTime());
    }

    public Recipe(String name, String description, String createdBy, String cuisine) {
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.cuisine = cuisine;
        this.dateCreated = new Timestamp(new Date().getTime());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Set<RecipeElement> getRecipeElements() {
        return recipeElements;
    }

    public void addRecipeElement(RecipeElement recipeElement) {
        recipeElements.add(recipeElement);
    }

    public void removeRecipeElement(RecipeElement recipeElement) {
        recipeElements.remove(recipeElement);
    }
}
