package ca.uwaterloo.pricecompare.models;

import java.util.ArrayList;
import java.util.List;

public class Product {
  private String name;
  private String categoryId;
  private List<String> viewedBy;

  public Product() {}

  public Product(String name, String categoryId) {
    this.name = name;
    this.categoryId = categoryId;
    this.viewedBy = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public String getCategoryId() {
    return categoryId;
  }

  public List<String> getViewedBy() {
    return viewedBy;
  }

  @Override
  public String toString() {
    return "Product{" +
        "name='" + name + '\'' +
        ", categoryId='" + categoryId + '\'' +
        '}';
  }
}
