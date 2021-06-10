package com.example.myapplication;

public class HelperClass {
    String name,phone,password,type;

    public HelperClass() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HelperClass(String name, String phone, String password, String type) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.type= type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
