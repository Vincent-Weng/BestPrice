package ca.uwaterloo.pricecompare.models;

public class Item {
  private String upc;
  private String storeId;
  private double price;

  public Item() {}

  public Item(String upc, String storeId, double price) {
    this.upc = upc;
    this.storeId = storeId;
    this.price = price;
  }

  public String getUpc() {
    return upc;
  }

  public String getStoreId() {
    return storeId;
  }

  public double getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return "Item{" +
        "UPC='" + upc + '\'' +
        ", store_id='" + storeId + '\'' +
        ", price=" + price +
        '}';
  }
}
