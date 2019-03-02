package ca.uwaterloo.ece651.pricecompare.DataReq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import android.util.Log;

import java.util.List;
//import ca.uwaterloo.ece651.pricecompare.DataReq.GetRequest_Interface;


public class GetRequest {
    private String baseUrl;
    private List<MyRequest> mMyResults;
    private GetRequest_Interface myRequester;

    public List<MyRequest> getMyRequest(){
        return mMyResults;
    }

    public GetRequest_Interface getMyRequester() {
        return myRequester;
    }

    public GetRequest(){
        baseUrl = "http://ece651.us-east-2.elasticbeanstalk.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        myRequester = retrofit.create(GetRequest_Interface.class);
    }

    public GetRequest(String baseUrl) {
        baseUrl = baseUrl;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        myRequester = retrofit.create(GetRequest_Interface.class);
    }


    public void request() {
        //对 发送请求 进行封装

        Call<List<MyRequest>> call = getMyRequester().getCalls();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<List<MyRequest>>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<List<MyRequest>> call, Response<List<MyRequest>> responses) {
                // 步骤7：处理返回的数据结果
                if(responses.body() == null){
                    //Toast.makeText(getBaseContext)
                    Log.d("From getrequest","responses are null");
                }
                else {
                    Log.d("GR:responses: ", responses.body().toString());
                    mMyResults = responses.body();
                    Log.d("GR:size of mMyRequest", Integer.toString(mMyResults.size()));
                    mMyResults.get(0).show();
                }
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<List<MyRequest>> call, Throwable throwable) {
                System.out.println("Failed to connect, cause:" + throwable.getCause().toString() + "message" + throwable.getLocalizedMessage().toString());
            }
        });
    }

    public void requestByUpc(String upc) {
        //对 发送请求 进行封装

        Call<List<MyRequest>> call = getMyRequester().getCallByUpc(upc);

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<List<MyRequest>>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<List<MyRequest>> call, Response<List<MyRequest>> responses) {
                // 步骤7：处理返回的数据结果
                if(responses.body() == null){
                    //Toast.makeText(getBaseContext)
                    Log.d("From getrequest","responses are null");
                }
                else {
                    Log.d("GR:responses: ", responses.body().toString());
                    mMyResults = responses.body();
                    Log.d("GRbyUPC:size mMyRequest", Integer.toString(mMyResults.size()));
                    mMyResults.get(0).show();
                }
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<List<MyRequest>> call, Throwable throwable) {
                System.out.println("Failed to connect, cause:" + throwable.getCause().toString() + "message" + throwable.getLocalizedMessage().toString());
            }
        });
    }

    public void reqCreateProduct(String upc, String name, String category, String picture) {

        //对 发送请求 进行封装


        Call<List<MyRequest>> call = getMyRequester().createProduct(upc, name, category, picture);
        //Call<MyRequest> call = request.getCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<List<MyRequest>>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<List<MyRequest>> call, Response<List<MyRequest>> responses) {
                // 步骤7：处理返回的数据结果
                if(responses.body() == null){
                    //Toast.makeText(getBaseContext)
                    Log.d("From getrequest","response.body() is null");
                }
                else {
                    //response.body().show();
                    Log.d("GR:responses: ", responses.body().toString());
                    mMyResults = responses.body();
                    Log.d("GR:size of mMyRequest", Integer.toString(mMyResults.size()));
                    mMyResults.get(0).show();
                }
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<List<MyRequest>> call, Throwable throwable) {
                System.out.println("Failed to connect");
            }
        });
    }

    public void testConnection() {

        //对 发送请求 进行封装


        Call<List<MyRequest>> call = getMyRequester().getCalls();
        //Call<MyRequest> call = request.getCall();

        //步骤6:发送网络请求(异步)
//        call.enqueue(new Callback<MyRequest>() {
//            //请求成功时回调
//            @Override
//            public void onResponse(Call<MyRequest> call, Response<MyRequest> response) {
//                // 步骤7：处理返回的数据结果
//                if(response.body() == null){
//                    //Toast.makeText(getBaseContext)
//                    Log.d("From getrequest","response.body() is null");
//                }
//                else {
//                    response.body().show();
//                }
//            }
//
//            //请求失败时回调
//            @Override
//            public void onFailure(Call<MyRequest> call, Throwable throwable) {
//                System.out.println("Failed to connect" );
//            }
//        });
    }
}