package tsugumi.seii.bankai.advisoryapplication;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import tsugumi.seii.bankai.advisoryapplication.model.LoginResponse;

public interface AdvisoryAppsService {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @GET("listing")
    Call<ResponseBody> retrieveListing(@Query("id") String id, @Query("token") String token);
}
