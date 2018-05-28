package navigation.maps.sharing.location.address.digital.app.topo.topolib.retrofit;


import navigation.maps.sharing.location.address.digital.app.topo.topolib.holders.ResponseData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by User on 3/14/2018.
 */

public interface APIInterface {


    @FormUrlEncoded
    @POST("/app/api_v1_create_sdk.php")
    Call<ResponseData> doCreateTopo(@Field("source") String source, @Field("topoId") String topoId, @Field("mobile") String mobile, @Field("topo_lat") String topo_lat, @Field("topo_lng") String topo_lng, @Field("flat_door") String flat_door, @Field("topo_address") String topo_address, @Field("topo_country") String topo_country, @Field("topo_city") String topo_city, @Field("topo_pincode") String topo_pincode, @Field("country") String country, @Field("apiKey2") String apiKey);

    @GET("/app/history_v1.php")
    Call<ResponseData> doGetHisrtory(@Query("source") String source, @Query("mobile") String mobile, @Query("country") String country, @Query("api") String apiKey);

    @FormUrlEncoded
    @POST("/app/api_v1_validate_sdk.php")
    Call<ResponseData> doGetTopo(@Field("oldTopoId") String oldTopoId, @Field("apiKey") String apiKey);

}
