package com.example.cookbook;

import java.util.HashMap;
import java.util.List;

public class DeserializedRecipe {
    private String name;
    private String description;
    private String createdBy;
    private String cuisine;

    private List<HashMap<String, HashMap<String, String>>> ingredientsAndQuantities;

    public DeserializedRecipe(String name, String description, String createdBy, String cuisine, List<HashMap<String, HashMap<String, String>>> ingredientsAndQuantities) {
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.cuisine = cuisine;
        this.ingredientsAndQuantities = ingredientsAndQuantities;
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

    public List<HashMap<String, HashMap<String, String>>> getIngredientsAndQuantities() {
        return ingredientsAndQuantities;
    }

    public void setIngredientsAndQuantities(List<HashMap<String, HashMap<String, String>>> ingredientsAndQuantities) {
        this.ingredientsAndQuantities = ingredientsAndQuantities;
    }
}
