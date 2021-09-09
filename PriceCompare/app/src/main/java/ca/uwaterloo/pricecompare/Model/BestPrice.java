package ca.uwaterloo.pricecompare.model;

public class BestPrice {

  private String UPC;
  private String storeName;
  private double price;

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

}
