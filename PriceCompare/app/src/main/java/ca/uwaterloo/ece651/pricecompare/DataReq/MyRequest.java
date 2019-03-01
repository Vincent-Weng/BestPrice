package ca.uwaterloo.ece651.pricecompare.DataReq;

import android.util.Log;

public class MyRequest{
    private Product product;
    private Store store;
    private Stock stock;

    //process the data
    public void show(){
        Log.d("product", product.getUPC());
    }

}

