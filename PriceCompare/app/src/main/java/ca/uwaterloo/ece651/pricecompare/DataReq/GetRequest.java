package ca.uwaterloo.ece651.pricecompare.DataReq;


import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Item;
import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Product;
import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Stock;
import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Store;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import android.util.Log;

import java.util.List;
//import java.util.Observable;

//import ca.uwaterloo.ece651.pricecompare.DataReq.GetRequest_Interface;


public class GetRequest {
    private String baseUrl;
    private List<Product> productResults;
    private List<Stock> stockResults;
    private List<Store> storeResults;
    private List<Item> itemResults;
    private GetRequest_Interface myRequester;
    private GetDataCallBack cb;



    public List<Product> getProductResults(){
        return productResults;

        //return testcb();
    }

//    public List<Product> testcb(){
//        //return productResults;
//        request(new GetDataCallBack() {
//            @Override
//            public void onGetData(Response rp) {
//                productResults = (List<Product>)rp.body();
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
//        return productResults;
//    }

    public void setProductResults(List<Product> productlists) {
        productResults = productlists;
    }
    public List<Stock> getStockResults(){
        return stockResults;
    }
    public List<Store> getStoreResults(){
        return storeResults;
    }
    public List<Item> getItemResults(){
        return itemResults;
    }

    public GetRequest_Interface getMyRequester() {
        return myRequester;
    }

    //Constructors

    public GetRequest(){
        baseUrl = "http://ece651.us-east-2.elasticbeanstalk.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) // setup http request Url
                .addConverterFactory(GsonConverterFactory.create()) //Use Gson to analyse the json objects
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        myRequester = retrofit.create(GetRequest_Interface.class);
    }

    public GetRequest(String baseUrl) {
        baseUrl = baseUrl;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) // setup http request Url
                .addConverterFactory(GsonConverterFactory.create()) //Use Gson to analyse the json objects
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        myRequester = retrofit.create(GetRequest_Interface.class);
    }

//    public List<Product> requestProductI(Callable<Product> func) {
//        //func = getMyRequester.getCalls()
//        Call<List<Product>> call = func;
//
//        //(async) send http request
//        call.enqueue(new Callback<List<Product>>() {
//            //callback when succeed requesting
//            @Override
//            public void onResponse(Call<List<Product>> call, Response<List<Product>> responses) {
//                // process return data
//                if(responses.body() == null){
//                    Log.d("From getrequest","responses are null");
//                }
//                else {
//                    Log.d("GR:responses: ", responses.body().toString());
//                    productResults = responses.body();
//                    Log.d("GR:size of mMyRequest", Integer.toString(productResults.size()));
//                    productResults.get(0).show();
//                }
//            }
//
//            //callback when failed requesting
//            @Override
//            public void onFailure(Call<List<Product>> call, Throwable throwable) {
//                System.out.println("Failed to connect, cause:" + throwable.getCause().toString() + "message" + throwable.getLocalizedMessage().toString());
//            }
//        });
//    }

//    //test callable
//    public void requestByF() {
//        Callable<List<Product>> func = getMyRequester().getCalls();
//        requestI(func);
//    }
    //method used to test the retrofit connection

    public void request() {

        Observable<List<Product>> observable = getMyRequester().getCalls();

        //(async) send http request
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Product>>() {
            //callback when succeed requesting
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("rxjava", "start subscribing");
            }

            @Override
            public void onNext(List<Product> result) {
                // process return data
                if(result == null){
                    Log.d("From getrequest","responses are null");
                }
                else {
                    setProductResults(result);
                    //Toast.makeText(getBaseContext(), "" + result.get(0).getMsg(), Toast.LENGTH_LONG);
                    result.get(0).show();
                }
            }

            //callback when failed requesting
            @Override
            public void onError(Throwable throwable) {
                System.out.println("Failed to connect, cause:" + throwable.getCause().toString() + "message" + throwable.getLocalizedMessage().toString());
            }

            @Override
            public void onComplete() {
                Log.d("rxjava", "success");
            }
        });
    }

    public void getProduct(String upc) {
        Call<List<Product>> call = getMyRequester().getProduct(upc);

        //(async) send http request
        call.enqueue(new Callback<List<Product>>() {
            //callback when succeed requesting
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> responses) {
                // process return data
                if(responses.body() == null){
                    //Toast.makeText(getBaseContext)
                    Log.d("From getrequest","responses are null");
                }
                else {
                    Log.d("GR:responses: ", responses.body().toString());
                    productResults = responses.body();
                    Log.d("GRbyUPC:size mMyRequest", Integer.toString(productResults.size()));
                    productResults.get(0).show();
                }
            }

            //callback when failed requesting
            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                System.out.println("Failed to connect, cause:" + throwable.getCause().toString() + "message" + throwable.getLocalizedMessage().toString());
            }
        });
    }

    public void createProduct(String upc, String name, int category, String picture) {

        Call<List<Product>> call = getMyRequester().createProduct(upc, name, category, picture);


        //(async) send http request
        call.enqueue(new Callback<List<Product>>() {
            //callback when succeed requesting
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> responses) {
                // process return data
                if(responses.body() == null){
                    //Toast.makeText(getBaseContext)
                    Log.d("From onResponse","response.body() is null");
                }
                else {
                    productResults = responses.body();
                    Log.d("GR:size of mMyRequest", Integer.toString(productResults.size()));
                    productResults.get(0).show();
                }
            }

            //callback when failed requesting
            @Override
            public void onFailure(Call<List<Product>> call, Throwable throwable) {
                System.out.println("Failed to connect");
            }
        });
    }

    public void updateProductName(String upc, String newName) {

        //Call<List<Product>> call = getMyRequester().getCalls();
        //Call<MyRequest> call = request.getCall();

        //(async) send http request
//        call.enqueue(new Callback<MyRequest>() {
//            //callback when succeed requesting
//            @Override
//            public void onResponse(Call<MyRequest> call, Response<MyRequest> response) {
//                // process return data
//                if(response.body() == null){
//                    //Toast.makeText(getBaseContext)
//                    Log.d("From getrequest","response.body() is null");
//                }
//                else {
//                    response.body().show();
//                }
//            }
//
//             //callback when failed requesting
//            @Override
//            public void onFailure(Call<MyRequest> call, Throwable throwable) {
//                System.out.println("Failed to connect" );
//            }
//        });
    }
}