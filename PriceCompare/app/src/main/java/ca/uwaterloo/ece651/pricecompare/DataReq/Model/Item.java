package ca.uwaterloo.ece651.pricecompare.DataReq.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.util.Log;


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
