package ca.uwaterloo.ece651.pricecompare.DataReq;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.POST;
import retrofit2.http.Query;
import java.util.List;

public interface GetRequest_Interface{
    //http://ece651.us-east-2.elasticbeanstalk.com/Product/Query/12345678910
    @GET("/Product/Query/{UPC}")
    Call<List<MyRequest>> getCallByUpc(@Path("UPC") String upc);

    @GET("/Product/Query/12345678910")
    Call<List<MyRequest>> getCalls();

    //@POST("/Product/Insert?upc={UPC}?name={name}?category={category}?picture={picture}")

//    Call<MyRequest> createProduct(@Path("UPC") String upc,
//                                 @Path("name") String name,
//                                 @Path("category") String category,
//                                 @Path("picture") String picture
//                                  );


    @POST("/Product/Insert")
    Call<List<MyRequest>> createProduct(@Query("UPC") String upc,
                                  @Query("name") String name,
                                  @Query("category") String category,
                                  @Query("picture") String picture
    );
    //do we really need to create a new store?
    //TODO
//    @POST("/Store/Insert?storeID={storeID}?storename={storename}?address={address}?latitude={latitude}" +
//            "?longtitude={longtitude}?city={city}?province={province}?postcode={postcode}")
//    Call<MyRequest> createStore()


}