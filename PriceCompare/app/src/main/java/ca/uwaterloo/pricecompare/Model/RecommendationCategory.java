package ca.uwaterloo.pricecompare.model;

public class RecommendationCategory {

  private String UPC;
  private double discount;
  private String name;
  private double price;
  private String storeName;

  public String getUPC() {
    return UPC;
  }

  public void setUPC(String UPC) {
    this.UPC = UPC;
  }

  public double getDiscount() {
    return discount;
  }

  public void setDiscount(double discount) {
    this.discount = discount;
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

  public String getStoreName() {
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

}
