package com.gradecalculator;

import java.util.List;

public class RA {
    private int id;
    private int raNumber;
    private float weight;
    private float score;
    private List<Component> components;

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRaNumber() {
        return raNumber;
    }

    public void setRaNumber(int raNumber) {
        this.raNumber = raNumber;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
}
