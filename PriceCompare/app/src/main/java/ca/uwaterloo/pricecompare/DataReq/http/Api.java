package ca.uwaterloo.pricecompare.DataReq.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

  public static String baseUrl = "http://ece651back.us-east-2.elasticbeanstalk.com/";
  public static ApiService apiService;

  private Api() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
    apiService = retrofit.create(ApiService.class);
  }

  //singleton
  public static ApiService getApiService() {
    if (apiService == null) {
      synchronized (Api.class) {
        if (apiService == null) {
          new Api();
        }
      }
    }
    return apiService;
  }
}
