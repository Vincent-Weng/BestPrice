package ca.uwaterloo.ece651.pricecompare.DataReq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ca.uwaterloo.ece651.pricecompare.DataReq.GetRequest_Interface;

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
    public void request() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ece651.us-east-2.elasticbeanstalk.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<MyRequest> call = request.getCall();

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<MyRequest>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<MyRequest> call, Response<MyRequest> response) {
                // 步骤7：处理返回的数据结果
                response.body().show();
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<MyRequest> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
    }
}