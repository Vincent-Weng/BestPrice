package ca.uwaterloo.pricecompare.model;

import android.util.Log;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


//now deprecated
public class Item {

  @SerializedName("msg")
  @Expose
  private String msg;

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    msg = msg;
  }

  public void show() {
    Log.d("OpResult:", msg);
  }
}
