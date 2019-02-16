package tsugumi.seii.bankai.advisoryapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tsugumi.seii.bankai.advisoryapplication.model.ListingItem;
import tsugumi.seii.bankai.advisoryapplication.model.ListingResponse;
import tsugumi.seii.bankai.advisoryapplication.model.Status;

import static tsugumi.seii.bankai.advisoryapplication.Utility.getApiServiceInstance;

public class MainActivity extends AppCompatActivity {
    public static final String ID_ID = "ID_ID";
    public static final String TOKEN_ID = "TOKEN_ID";

    private TextView textView;
    private Button mSignOutButton;
    private Button mRetrieveListingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        textView = findViewById(R.id.textView);
        mSignOutButton = findViewById(R.id.sign_out_button);
        mRetrieveListingButton = findViewById(R.id.retrieve_listing_button);

        final String id= getIntent().getStringExtra(ID_ID);
        final String token= getIntent().getStringExtra(TOKEN_ID);
        mRetrieveListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriveListing(id,token);
            }
        });

        mSignOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

    }

    private void retriveListing(String id, String token){
        Call<ListingResponse> call = getApiServiceInstance().retrieveListing(id,token);
        call.enqueue(new Callback<ListingResponse>() {
            @Override
            public void onResponse(Call<ListingResponse> call, Response<ListingResponse> response) {
                ListingResponse listingResponse = response.body();
                Status responseStatus = listingResponse.getStatus();

                //showProgress(false);
                if(response.isSuccessful() && responseStatus.isSuccessful()){
                    String gg = "";
                    for(ListingItem l : listingResponse.getListing()){
                        gg = gg.concat(l.getListName());
                    }
                    textView.setText(gg);
                } else {
                    // TODO something went wrong, do it again
                }
            }

            @Override
            public void onFailure(Call<ListingResponse> call, Throwable t) {
                // TODO notify users, on network failures etc
//                showProgress(false);
//                Toast.makeText(LoginActivity.this,"Please ensure that you are connected to the internet.",Toast.LENGTH_LONG).show();
            }
        });
    }
}
