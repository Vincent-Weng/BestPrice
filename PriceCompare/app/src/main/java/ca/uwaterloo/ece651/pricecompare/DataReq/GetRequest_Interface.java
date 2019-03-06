package ca.uwaterloo.ece651.pricecompare.DataReq;

import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Product;
import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Stock;
import ca.uwaterloo.ece651.pricecompare.DataReq.Model.Store;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import java.util.List;
import io.reactivex.Observable;

public interface GetRequest_Interface{
    //http://ece651.us-east-2.elasticbeanstalk.com/Product/Query/12345678910
    //----------Product-------------------------
    @GET("/Product/Query/12345678910")
    Observable<List<Product>> getCalls();

    @GET("/Product/Query/{UPC}")
    Call<List<Product>> getProduct(@Path("UPC") String upc);

    @GET("/Product/Insert/")
    Call<List<Product>> createProduct(@Query("UPC") String upc,
                                  @Query("name") String name,
                                  @Query("category") int category,
                                  @Query("picture") String picture);

    @GET("/Product/Updatename")
    Call<List<Product>> updateProductName(@Query("UPC") String upc,
                                       @Query("name") String newName);

    @GET("/Product/Updatecategory")
    Call<List<Product>> updateProductCategory(@Query("UPC") String upc,
                                           @Query("category") int newCategory);

    @GET("/Product/Updatepicture")
    Call<List<Product>> updateProductPicture(@Query("UPC") String upc,
                                               @Query("picture") int newPicture);

    @GET("/Product/Update/")
    Call<List<Product>> updateProduct(@Query("UPC") String upc,
                                       @Query("name") String name,
                                       @Query("category") int category  //,
                                       //@Query("picture") String picture
    );

    @GET("/Product/Delete/{UPC}")
    Call<List<Product>> deleteProduct(@Path("UPC") String upc);


   //-------------------Stock---------------------
    @GET("/Stock/Insert")
    Call<List<Stock>> createStock(@Query("storename") String storename,
                                  @Query("UPC") String UPC,
                                  @Query("price") double price);

    @GET("/Stock/Query/{UPC}/{storename}")
    Call<List<Stock>> getStock(@Path("UPC") String upc,
                                   @Path("storename") String storename);

    @GET("/Stock/Update/")
    Call<List<Stock>> updateStock(@Query("UPC") String upc,
                                  @Query("storename") String storename,
                                  @Query("price") double price);

    @GET("/Stock/Delete/{UPC}/{storename}")
    Call<List<Stock>> deleteStock(@Path("UPC") String upc,
                                 @Path("storename") String storename);


    //------------------Store----------------------

    @GET("/Store/Insert")
    Call<List<Store>> createStore(@Query("storename") String storename,
                                  @Query("address") String address,
                                  @Query("latitude") double latitude,
                                  @Query("longitude") double longitude,
                                  @Query("city") String city,
                                  @Query("postcode") double postcode);

    @GET("/Store/Query/{storename}")
    Call<List<Store>> getStore(@Path("storename") String storename);

    //this actually return a string array!
    @GET("/Store/Queryallstore")
    Call<List<String>> getAllStores();

    @GET("/Store/Update/")
    Call<List<Store>> updateStore(@Query("storename") String storename,
                                  @Query("address") String address,
                                  @Query("latitude") double latitude,
                                  @Query("longitude") double longitude,
                                  @Query("city") String city,
                                  @Query("postcode") double postcode);

    @GET("/Store/Updatestoreaddress/")
    Call<List<Store>> updateStoreAddress(@Query("storename") String storename,
                                  @Query("address") String address,
                                  @Query("latitude") double latitude,
                                  @Query("longitude") double longitude,
                                  @Query("city") String city,
                                  @Query("postcode") double postcode);

    @GET("/Store/Updatestorelatitude/")
    Call<List<Store>> updateStoreLatitude(@Query("storename") String storename,
                                         @Query("address") String address,
                                         @Query("latitude") double latitude,
                                         @Query("longitude") double longitude,
                                         @Query("city") String city,
                                         @Query("postcode") double postcode);

    @GET("/Store/UpdatestoreLongitude/")
    Call<List<Store>> updateStoreLongitude(@Query("storename") String storename,
                                         @Query("address") String address,
                                         @Query("latitude") double latitude,
                                         @Query("longitude") double longitude,
                                         @Query("city") String city,
                                         @Query("postcode") double postcode);

    @GET("/Store/Updatestorecity/")
    Call<List<Store>> updateStoreCity(@Query("storename") String storename,
                                         @Query("address") String address,
                                         @Query("latitude") double latitude,
                                         @Query("longitude") double longitude,
                                         @Query("city") String city,
                                         @Query("postcode") double postcode);

    @GET("/Store/Updatestoreprovince/")
    Call<List<Store>> updateStoreProvince(@Query("storename") String storename,
                                         @Query("address") String address,
                                         @Query("latitude") double latitude,
                                         @Query("longitude") double longitude,
                                         @Query("city") String city,
                                         @Query("postcode") double postcode);

    @GET("/Store/Updatestorepostcode/")
    Call<List<Store>> updateStorePostcode(@Query("storename") String storename,
                                          @Query("address") String address,
                                          @Query("latitude") double latitude,
                                          @Query("longitude") double longitude,
                                          @Query("city") String city,
                                          @Query("postcode") double postcode);

    @GET("/Store/Delete/{storename}")
    Call<List<Store>> deleteStore(@Path("storename") String storename);

    //-------------------Item------------------------------
    //TODO: createItem()??
//
//    @GET("/Item/Insert/")
//    Call<List<Item>> createProduct(
//                                        @Query("newstoreflag") String upc,
//                                        @Query("productname") String name,
//                                        @Query("UPC") String upc,
//                                      @Query("productname") String name,
//                                      @Query("category") int category,
//                                      @Query("picture") String picture);

}