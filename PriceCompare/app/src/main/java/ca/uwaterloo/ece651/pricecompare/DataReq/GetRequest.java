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

//public class GetRequest implements AppCompatActivity {
//    @Override
//    protected void OnCreate (Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        request();
//        // 使用Retrofit封装的方法
//    }
public class GetRequest {
    private List<MyRequest> mMyRequest;

    public List<MyRequest> getMyRequest(){
        return mMyRequest;
    }

    public void request() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ece651.us-east-2.elasticbeanstalk.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装

        Call<List<MyRequest>> call = request.getCalls();

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
                    mMyRequest = responses.body();
                    Log.d("GR:size of mMyRequest", Integer.toString(mMyRequest.size()));
                    mMyRequest.get(0).show();
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

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ece651.us-east-2.elasticbeanstalk.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装


        Call<MyRequest> call = request.createProduct(upc, name, category, picture);
        //Call<MyRequest> call = request.getCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<MyRequest>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<MyRequest> call, Response<MyRequest> response) {
                // 步骤7：处理返回的数据结果
                if(response.body() == null){
                    //Toast.makeText(getBaseContext)
                    Log.d("From getrequest","response.body() is null");
                }
                else {
                    response.body().show();
                }
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<MyRequest> call, Throwable throwable) {
                System.out.println("Failed to connect");
            }
        });
    }

    public void testConnection() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ece651.us-east-2.elasticbeanstalk.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装


        Call<List<MyRequest>> call = request.getCalls();
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