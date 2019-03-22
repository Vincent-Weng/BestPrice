package com.example.ece651BestPrice.bean;

public class RStore {
    private String UPC;
    private String name;
    private double price;
    private String storename;
    private String category;
    private double minprice;

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getMinprice() {
        return minprice;
    }

    public void setMinprice(double minprice) {
        this.minprice = minprice;
    }
}
