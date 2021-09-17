package ca.uwaterloo.pricecompare.models;

public class Product {
  private String name;
  private String categoryId;

  public Product() {}

  public Product(String name, String categoryId) {
    this.name = name;
    this.categoryId = categoryId;
  }

  public String getName() {
    return name;
  }

  public String getCategoryId() {
    return categoryId;
  }

  @Override
  public String toString() {
    return "Product{" +
        "name='" + name + '\'' +
        ", categoryId='" + categoryId + '\'' +
        '}';
  }
}
