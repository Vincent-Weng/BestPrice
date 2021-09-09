package ca.uwaterloo.pricecompare.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store {

  @SerializedName("storename")
  @Expose
  private String storeName;

  @SerializedName("address")
  @Expose
  private String address;

  @SerializedName("latitude")
  @Expose
  private double latitude;

  @SerializedName("longitude")
  @Expose
  private double longitude;

  @SerializedName("city")
  @Expose
  private String city;

  @SerializedName("province")
  @Expose
  private String province;

  @SerializedName("postcode")
  @Expose
  private String postcode;

  @SerializedName("msg")
  @Expose
  private String msg;


  public String getStoreName() {
    return storeName;
  }

  public void setStoreName(String storeName) {
    this.storeName = storeName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
