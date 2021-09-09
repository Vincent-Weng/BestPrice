package ca.uwaterloo.pricecompare.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stock {

  @SerializedName("UPC")
  @Expose
  private String UPC;

  @SerializedName("storename")
  @Expose
  private String storeName;

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
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
