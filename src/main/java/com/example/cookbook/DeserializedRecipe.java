package com.example.cookbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeserializedRecipe {

    private int id;

    private int serves;
    private String name;
    private List<String> description;
    private String createdBy;
    private String cuisine;

    private List<Map<String, Map<String, String>>> ingredientsAndQuantities;

    public DeserializedRecipe(int id, int serves, String name, List<String> description, String createdBy, String cuisine, List<Map<String, Map<String, String>>> ingredientsAndQuantities) {
        this.id = id;
        this.serves = serves;
        this.name = name;
        this.description = description;
        this.createdBy = createdBy;
        this.cuisine = cuisine;
        this.ingredientsAndQuantities = ingredientsAndQuantities;
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

    public int getServes() {
        return serves;
    }

    public void setServes(int serves) {
        this.serves = serves;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
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

    public List<Map<String, Map<String, String>>> getIngredientsAndQuantities() {
        return ingredientsAndQuantities;
    }

    public void setIngredientsAndQuantities(List<Map<String, Map<String, String>>> ingredientsAndQuantities) {
        this.ingredientsAndQuantities = ingredientsAndQuantities;
    }
}
