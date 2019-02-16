package tsugumi.seii.bankai.advisoryapplication;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Convenient class for commonly used methods
 */
public class Utility {
    public static final String BASE_URL = "http://interview.advisoryapps.com/index.php/";

    /**
     * Convenient method to perform Assert logging \(⊙ꇴ⊙)/
     * @param msg assert log message
     */
    public static void aLog(String msg){
        Log.println(Log.ASSERT,"\"(⊙ꇴ⊙)/",""+msg);
    }

    /**
     * Returns instantiated service interface to connect to the api with Retrofit.
     * Gson converter is configured to parse JSON results.
     *
     * @return instantiated service interface to connect to the api with Retrofit
     */
    public static AdvisoryAppsService getApiServiceInstance(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(AdvisoryAppsService.class);
    }

}
