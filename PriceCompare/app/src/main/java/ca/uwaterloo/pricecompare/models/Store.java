package ca.uwaterloo.pricecompare.models;

import com.google.firebase.firestore.GeoPoint;

/**
 * This class is EXPERIMENTAL. For now the names are hardcoded and used as unique values in UI.
 *
 * They are hardcoded FOR DEMO PURPOSES ONLY.
 */
public class Store {

  private String address;
  private String city;
  private String postal_code;
  private String prov_state;
  private String name;
  private GeoPoint location;
  private String id;
  private int imageId;

  public Store() {}

  public void setId(String id) {
    this.id = id;
  }

  public void setImageId(int imageId) {
    this.imageId = imageId;
  }

  public String getAddress() {
    return address;
  }

  public String getCity() {
    return city;
  }

  public String getPostal_code() {
    return postal_code;
  }

  public String getProv_state() {
    return prov_state;
  }

  public String getName() {
    return name;
  }

  public GeoPoint getLocation() {
    return location;
  }

  public String getId() {
    return id;
  }

  public int getImageId() {
    return imageId;
  }
}
