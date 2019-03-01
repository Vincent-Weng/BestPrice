package ca.uwaterloo.ece651.pricecompare.DataReq;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRequest_Interface{
    //http://ece651.us-east-2.elasticbeanstalk.com/Product/Query/12345678910
    @GET("/Product/Query/12345678910")
    Call<MyRequest> getCall();
}