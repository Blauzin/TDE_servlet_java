package com.gradecalculator;

import java.util.List;

public class Subject {
    private int id;
    private String name;
    private List<RA> ras;

    // Getters e Setters
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

    public List<RA> getRas() {
        return ras;
    }

    public void setRas(List<RA> ras) {
        this.ras = ras;
    }
}