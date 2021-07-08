package com.example.myapplication;

public class HelperClass2 {
    String name;
    String price;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public HelperClass2(String name, String price, String phone) {
        this.name = name;
        this.price = price;
        this.phone = phone;
    }

    String phone;
}
