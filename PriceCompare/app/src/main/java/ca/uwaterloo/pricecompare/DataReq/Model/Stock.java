package ca.uwaterloo.pricecompare.DataReq.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stock {
    @SerializedName("UPC")
    @Expose
    private String UPC;

    @SerializedName("storename")
    @Expose
    private String storename;

    @SerializedName("price")
    @Expose
    private double price;

    @SerializedName("msg")
    @Expose
    private String msg;

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC;
    }

    public String getStoreName() {
        return storename;
    }

    public void setStoreName(String storeName) {
        this.storename = storeName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
