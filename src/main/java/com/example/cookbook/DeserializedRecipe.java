package com.example.cookbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DeserializedRecipe {

    private int id;

    private int serves;
    private String name;
    private ArrayList<String> description;
    private String createdBy;
    private String cuisine;

    private List<HashMap<String, HashMap<String, String>>> ingredientsAndQuantities;

    public DeserializedRecipe(int id, int serves, String name, ArrayList<String> description, String createdBy, String cuisine, List<HashMap<String, HashMap<String, String>>> ingredientsAndQuantities) {
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

    public ArrayList<String> getDescription() {
        return description;
    }

    public void setDescription(ArrayList<String> description) {
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
