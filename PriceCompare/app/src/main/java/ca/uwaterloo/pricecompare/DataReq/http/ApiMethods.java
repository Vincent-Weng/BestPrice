package ca.uwaterloo.pricecompare.DataReq.http;

import ca.uwaterloo.pricecompare.DataReq.Model.BestPrice;
import ca.uwaterloo.pricecompare.DataReq.Model.Item;
import ca.uwaterloo.pricecompare.DataReq.Model.Product;
import ca.uwaterloo.pricecompare.DataReq.Model.RecommendationCategory;
import ca.uwaterloo.pricecompare.DataReq.Model.RecommendationStore;
import ca.uwaterloo.pricecompare.DataReq.Model.Stock;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java.util.List;

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


    //----------------Recommendation by category-----------------
    public static void getRecommendationByCategory(Observer<List<RecommendationCategory>> observer,
                                       int category){
        ApiSubscribe(Api.getApiService().getRecommendationByCategory(category), observer);
    }

    //-----------------Recommendation by store--------------------------------------------
    public static void getRecommendationByStore(Observer<List<RecommendationStore>> observer,
                                    String storename){
        ApiSubscribe(Api.getApiService().getRecommendationByStore(storename), observer);
    }

    //-----------------Get the best price--------------------------------------------------
    public static void getBestPrice(Observer<List<BestPrice>> observer,
                                       String UPC){
        ApiSubscribe(Api.getApiService().getBestPrice(UPC), observer);
    }
}
