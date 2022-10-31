package com.example.foodandbeverages;

public class FoodModel {
    public  String id;
    public  String name;
    public  String imageUrl;
    public  double price;
    public  boolean inStock;

    public FoodModel(String id, String name, String imageUrl, double price, boolean inStock) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.inStock = inStock;
    }
}
