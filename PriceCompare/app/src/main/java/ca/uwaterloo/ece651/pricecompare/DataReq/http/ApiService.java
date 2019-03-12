package ca.uwaterloo.ece651.pricecompare.DataReq.http;

import java.util.List;

import ca.uwaterloo.ece651.pricecompare.DataReq.Model.*;
import io.reactivex.Observable;
import retrofit2.http.*;


public interface ApiService {
    @GET("/Product/Query/12345678910")
    Observable<List<Product>> getCalls();

    @GET("/Product/Query/{UPC}")
    Observable<List<Product>> getProduct(@Path("UPC") String upc);

    @GET("/Product/Insert/")
    Observable<List<Product>> createProduct(@Query("UPC") String upc,
                                      @Query("name") String name,
                                      @Query("category") int category,
                                      @Query("picture") String picture);

    @GET("/Product/Updatename")
    Observable<List<Product>> updateProductName(@Query("UPC") String upc,
                                          @Query("name") String newName);

    @GET("/Product/Updatecategory")
    Observable<List<Product>> updateProductCategory(@Query("UPC") String upc,
                                              @Query("category") int newCategory);

    @GET("/Product/Updatepicture")
    Observable<List<Product>> updateProductPicture(@Query("UPC") String upc,
                                             @Query("picture") int newPicture);

    @GET("/Product/Update/")
    Observable<List<Product>> updateProduct(@Query("UPC") String upc,
                                      @Query("name") String name,
                                      @Query("category") int category  //,
                                      //@Query("picture") String picture
    );

    @GET("/Product/Delete/{UPC}")
    Observable<List<Product>> deleteProduct(@Path("UPC") String upc);


    //-------------------Stock---------------------
    @GET("/Stock/Insert")
    Observable<List<Stock>> createStock(@Query("storename") String storename,
                                        @Query("UPC") String UPC,
                                        @Query("price") double price);

    @GET("/Stock/Query/{UPC}/{storename}")
    Observable<List<Stock>> getStock(@Path("UPC") String upc,
                               @Path("storename") String storename);

    @GET("/Stock/Update/")
    Observable<List<Stock>> updateStock(@Query("UPC") String upc,
                                  @Query("storename") String storename,
                                  @Query("price") double price);

    @GET("/Stock/Delete/{UPC}/{storename}")
    Observable<List<Stock>> deleteStock(@Path("UPC") String upc,
                                  @Path("storename") String storename);


    //------------------Store----------------------

    @GET("/Store/Insert")
    Observable<List<Store>> createStore(@Query("storename") String storename,
                                        @Query("address") String address,
                                        @Query("latitude") double latitude,
                                        @Query("longitude") double longitude,
                                        @Query("city") String city,
                                        @Query("postcode") double postcode);

    @GET("/Store/Query/{storename}")
    Observable<List<Store>> getStore(@Path("storename") String storename);

    //this actually return a string array!
    @GET("/Store/Queryallstore")
    Observable<List<String>> getAllStores();

    @GET("/Store/Update/")
    Observable<List<Store>> updateStore(@Query("storename") String storename,
                                  @Query("address") String address,
                                  @Query("latitude") double latitude,
                                  @Query("longitude") double longitude,
                                  @Query("city") String city,
                                  @Query("postcode") double postcode);

    @GET("/Store/Updatestoreaddress/")
    Observable<List<Store>> updateStoreAddress(@Query("storename") String storename,
                                         @Query("address") String address,
                                         @Query("latitude") double latitude,
                                         @Query("longitude") double longitude,
                                         @Query("city") String city,
                                         @Query("postcode") double postcode);

    @GET("/Store/Updatestorelatitude/")
    Observable<List<Store>> updateStoreLatitude(@Query("storename") String storename,
                                          @Query("address") String address,
                                          @Query("latitude") double latitude,
                                          @Query("longitude") double longitude,
                                          @Query("city") String city,
                                          @Query("postcode") double postcode);

    @GET("/Store/UpdatestoreLongitude/")
    Observable<List<Store>> updateStoreLongitude(@Query("storename") String storename,
                                           @Query("address") String address,
                                           @Query("latitude") double latitude,
                                           @Query("longitude") double longitude,
                                           @Query("city") String city,
                                           @Query("postcode") double postcode);

    @GET("/Store/Updatestorecity/")
    Observable<List<Store>> updateStoreCity(@Query("storename") String storename,
                                      @Query("address") String address,
                                      @Query("latitude") double latitude,
                                      @Query("longitude") double longitude,
                                      @Query("city") String city,
                                      @Query("postcode") double postcode);

    @GET("/Store/Updatestoreprovince/")
    Observable<List<Store>> updateStoreProvince(@Query("storename") String storename,
                                          @Query("address") String address,
                                          @Query("latitude") double latitude,
                                          @Query("longitude") double longitude,
                                          @Query("city") String city,
                                          @Query("postcode") double postcode);

    @GET("/Store/Updatestorepostcode/")
    Observable<List<Store>> updateStorePostcode(@Query("storename") String storename,
                                          @Query("address") String address,
                                          @Query("latitude") double latitude,
                                          @Query("longitude") double longitude,
                                          @Query("city") String city,
                                          @Query("postcode") double postcode);

    @GET("/Store/Delete/{storename}")
    Observable<List<Store>> deleteStore(@Path("storename") String storename);

    //-------------------Item------------------------------
    //TODO: createItem()??

    @GET
    Observable<List<Item>> createItem(@Url String url);





//    @GET("/Item/Insert?item={newstoreflag}?={productnamechangeflag}?={UPC}?={productname}?={category}?={storename}?={price}")
//    Observable<List<Item>> createItem(
//                                        @Path(value = "newstoreflag", encoded = true) int flag,
//                                        @Query(value ="productnamechangeflag", encoded = true) int productnamechangeflag,
//                                        @Query(value ="UPC", encoded = true) String upc,
//                                      @Query(value ="productname", encoded = true) String name,
//                                      @Query(value ="category", encoded = true) int category,
//                                      @Query(value ="storename", encoded = true) String storename,
//                                        @Query(value ="price", encoded = true) double price);

//    @GET("/Item/Insert?item={newstoreflag}?={productnamechangeflag}?={UPC}?={productname}?={category}?={storename}?={price}")
//    Observable<List<Item>> createItem(
//            @Path("newstoreflag") int flag,
//            @Path("productnamechangeflag") int productnamechangeflag,
//            @Query("UPC") String upc,
//            @Query("productname") String name,
//            @Query("category") int category,
//            @Query("storename") String storename,
//            @Query("price") double price);

}
