package ca.uwaterloo.pricecompare.DataReq.Model;

import android.util.Log;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product{

    //responses of querying product:
    @SerializedName("UPC")
    @Expose
    private String UPC;

    @SerializedName("name")
    @Expose
    private String name = null;

    @SerializedName("category")
    @Expose
    private String category = null;

    @SerializedName("picture")
    @Expose
    private String picture = null;

    @SerializedName("msg")
    @Expose
    private String msg;

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void show(){
        Log.d("upc:", "" + getUPC());
        Log.d("name:", "" + getName());
        Log.d("category:", "" + getCategory());
        Log.d("picture:", "" + getPicture());
        Log.d("msg", "" + getMsg());

    }
}


