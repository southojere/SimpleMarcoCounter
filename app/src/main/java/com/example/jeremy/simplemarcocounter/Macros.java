package com.example.jeremy.simplemarcocounter;

public class Macros {
    private double carbs;
    private double protein;
    private double fat;

    //private int id;
    public Macros(double c, double p, double f) {
        carbs = c;
        protein = p;
        fat = f;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public void setAll(double value) {
        this.fat = this.carbs = this.protein = value;

    }

    public double getCarbs() {
        return carbs;
    }

    public double getFat() {
        return fat;
    }

    public double getProtein() {
        return protein;
    }

    public void addCarbs(double nValue) {
        carbs += nValue;
    }

    public void addProtein(double nValue) {
        protein += nValue;
    }

    public void addFat(double nValue) {
        fat += nValue;
    }
}
