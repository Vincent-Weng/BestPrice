package ca.uwaterloo.pricecompare.DataReq.http;

import ca.uwaterloo.pricecompare.DataReq.Model.BestPrice;
import ca.uwaterloo.pricecompare.DataReq.Model.Item;
import ca.uwaterloo.pricecompare.DataReq.Model.Product;
import ca.uwaterloo.pricecompare.DataReq.Model.RecommendationCategory;
import ca.uwaterloo.pricecompare.DataReq.Model.RecommendationStore;
import ca.uwaterloo.pricecompare.DataReq.Model.Stock;
import ca.uwaterloo.pricecompare.DataReq.Model.Store;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;


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


    //--------------------Recommendation by category---------------
    @GET("/Display/querycategory/{category}")
    Observable<List<RecommendationCategory>> getRecommendationByCategory(@Path("category") int category);

    //--------------------Recommendation by category---------------
    @GET("/Display/querystore/{storename}")
    Observable<List<RecommendationStore>> getRecommendationByStore(@Path("storename") String storename);

    //--------------------Recommendation by category---------------
    @GET("/Display/bestprice/{UPC}")
    Observable<List<BestPrice>> getBestPrice(@Path("UPC") String UPC);

}
