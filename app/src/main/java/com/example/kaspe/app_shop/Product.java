package com.example.kaspe.app_shop;

/**
 * Created by kaspe on 22-05-2017.
 */

public class Product {
    public int id;
    public int category_id;
    public String name;
    public String price;
    public String description;
    public int amount;

    public Product(int id, int category_id, String name, String price, String description, int amount) {
        this.id = id;
        this.category_id = category_id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
