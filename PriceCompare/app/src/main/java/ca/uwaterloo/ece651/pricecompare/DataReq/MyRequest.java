package ca.uwaterloo.ece651.pricecompare.DataReq;

import android.util.Log;
import com.google.gson.annotations.*;

public class MyRequest{
//    private Product product;
//    private Store store;
//    private Stock stock;
//    private String jsonResponse;
//    //process the data
//    public void show(){
//        Log.d("product", product.getUPC());
//    }
    @SerializedName("UPC")
    @Expose
    private String UPC;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("category")
    @Expose
    private String category;

    @SerializedName("picture")
    @Expose
    private String picture;

    public String getUPC() {
        return UPC;
    }

    public void setUPCe(String UPC) {
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


    public void show(){
        Log.d("upc:", "" + getUPC());
        Log.d("name:", "" + getName());
        Log.d("category:", "" + getCategory());
        Log.d("picture:", "" + getPicture());

    }
}

//public class testResponse{
//    private String UPC;
//    private String name;
//    private String category;
//    private String picture;
//
//    public void show(){
//        Log.d("upc:", UPC);
//        Log.d("name:", name);
//        Log.d("category:", category);
//        Log.d("picture:", picture);
//
//    }
//}

