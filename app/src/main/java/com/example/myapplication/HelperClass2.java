package com.example.myapplication;

public class HelperClass2 {
    String name;
    String price;
    String img;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public HelperClass2(String name, String price, String phone, String img) {
        this.name = name;
        this.price = price;
        this.phone = phone;
        this.img=img;
    }

    String phone;
}
