package ca.uwaterloo.pricecompare.model;

public class RecommendationStore {

  private String UPC;
  private String category;
  private double minPrice;
  private String name;
  private double price;
  private String storeName;

  public String getUPC() {
    return UPC;
  }

  public void setUPC(String UPC) {
    this.UPC = UPC;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public double getMinPrice() {
    return minPrice;
  }

  public void setMinPrice(double minPrice) {
    this.minPrice = minPrice;
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
