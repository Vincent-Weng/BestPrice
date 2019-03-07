package ca.uwaterloo.ece651.pricecompare.DataReq.http;

import java.util.List;

import ca.uwaterloo.ece651.pricecompare.DataReq.Model.*;
import ca.uwaterloo.ece651.pricecompare.DataReq.http.Api;
import io.reactivex.Observer;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApiMethods {
    public static void ApiSubscribe(Observable observable, Observer observer) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

//---------------------Product------------------------

    public static void getCalls(Observer<List<Product>> observer) {
        ApiSubscribe(Api.getApiService().getCalls(), observer);
    }

    public static void createProduct(Observer<List<Product>> observer,
                                     String upc, String name, int category, String picture) {
        ApiSubscribe(Api.getApiService().createProduct(upc, name, category, picture), observer);
    }

    public static void getProduct(Observer<List<Product>> observer,
                                  String upc) {
        ApiSubscribe(Api.getApiService().getProduct(upc), observer);
    }

    public static void updateProduct(Observer<List<Product>> observer,
                                     String upc, String name, int category, String picture) {
        ApiSubscribe(Api.getApiService().updateProduct(upc, name, category), observer);
    }

//---------------------Stock------------------------

    public static void createStock(Observer<List<Stock>> observer,
                                   String storename, String upc, double price) {
        ApiSubscribe(Api.getApiService().createStock(storename, upc, price), observer);
    }

    public static void getStock(Observer<List<Stock>> observer,
                                String upc, String storename) {
        ApiSubscribe(Api.getApiService().getStock(upc, storename), observer);
    }

    public static void updateStock(Observer<List<Stock>> observer,
                                   String upc, String storename, double price) {
        ApiSubscribe(Api.getApiService().updateStock(upc, storename, price), observer);
    }

    public static void deleteStock(Observer<List<Stock>> observer,
                                   String upc, String storename) {
        ApiSubscribe(Api.getApiService().deleteStock(upc, storename), observer);
    }

    //---------------------Store------------------------

    public static void createStore(Observer<List<Stock>> observer,
                                   String storename, String address, double latitude, double longitude, String city, double postcode) {
        ApiSubscribe(Api.getApiService().createStore(storename, address, latitude, longitude, city, postcode), observer);
    }

    public static void getStore(Observer<List<Stock>> observer,
                                String storename) {
        ApiSubscribe(Api.getApiService().getStore(storename), observer);
    }

    public static void getAllStores(Observer<List<Stock>> observer) {
        ApiSubscribe(Api.getApiService().getAllStores(), observer);
    }

    public static void updateStore(Observer<List<Stock>> observer,
                                   String storename, String address, double latitude, double longitude, String city, double postcode) {
        ApiSubscribe(Api.getApiService().updateStore(storename, address, latitude, longitude, city, postcode), observer);
    }

    public static void deleteStore(Observer<List<Stock>> observer,
                                   String storename) {
        ApiSubscribe(Api.getApiService().deleteStore(storename), observer);
    }

    //---------------------Item------------------------
//    public static void createItem(Observer<List<Item>> observer,
//                                   int newstoreflag, int productnamechangeflag, String upc, String productname, int category, String storename, double price) {
//        ApiSubscribe(Api.getApiService().createItem(newstoreflag, productnamechangeflag, upc, productname, category, storename, price), observer);
//    }

    public static void createItem(Observer<List<Item>> observer,
                                String url){
        ApiSubscribe(Api.getApiService().createItem(url), observer);
    }
}
