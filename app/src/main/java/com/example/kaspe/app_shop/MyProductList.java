package com.example.kaspe.app_shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaspe on 23-05-2017.
 */

public final class MyProductList {
    private static final MyProductList SELF = new MyProductList();

    private List<Product> products = new ArrayList<Product>();
    private boolean didProducts;

    private MyProductList() {
        // Don't want anyone else constructing the singleton.
    }

    public static MyProductList getInstance() {
        return SELF;
    }

    public List<Product> getProducts() {
        return products;
    }
}
