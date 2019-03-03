package com.example.ece651BestPrice.util;

import java.util.*;

public class ProductDecoder {
    private boolean flagNewStore;
    private boolean flagChangeName;
    private String UPC;
    private String name;
    private int category;
    private String store;
    private double price;

    public ProductDecoder(String encodedString) {
        String[] fields = encodedString.split("\\?=");
        flagNewStore = fields[0].equals("1");
        flagChangeName = fields[1].equals("1");
        UPC = fields[2];
        name = fields[3].replace("&&", " ");
        category = Integer.valueOf(fields[4]);
        store = fields[5].replace("&&", " ");
        price = Double.valueOf(fields[6].replace('%', '.'));
    }

    public boolean getFlagNewStore() {
        return flagNewStore;
    }

    public boolean getFlagChangeName() {
        return flagChangeName;
    }

    public String getUPC() {
        return UPC;
    }

    public String getName() {
        return name;
    }

    public int getCategory() {
        return category;
    }

    public String getStore() {
        return store;
    }

    public double getPrice() {
        return price;
    }

}