package com.example.kaspe.app_shop;

/**
 * Created by kaspe on 23-05-2017.
 */


    public class Customer {


        private String email;
        private String name;
        private String id;

    public Customer(String email, String name, String id) {
        this.email = email;
        this.name = name;
        this.id = id;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
