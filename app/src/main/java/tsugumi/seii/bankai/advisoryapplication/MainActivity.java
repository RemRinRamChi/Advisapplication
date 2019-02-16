package tsugumi.seii.bankai.advisoryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tsugumi.seii.bankai.advisoryapplication.model.ListingItem;
import tsugumi.seii.bankai.advisoryapplication.model.ListingResponse;
import tsugumi.seii.bankai.advisoryapplication.model.Status;

import static tsugumi.seii.bankai.advisoryapplication.Utility.getApiServiceInstance;

/**
 * MainActivity allowing request for listing as well as logging out
 */
public class MainActivity extends AppCompatActivity {
    public static final String ID_ID = "ID_ID";
    public static final String TOKEN_ID = "TOKEN_ID";

    private View mProgressView;

    private ListingAdapter mListingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressView = findViewById(R.id.login_progress_page);
        RecyclerView listingRecyclerView = findViewById(R.id.listing_recycler_view);

        Button signOutButton = findViewById(R.id.sign_out_button);
        Button retrieveListingButton = findViewById(R.id.retrieve_listing_button);

        // initially nothing is in the list as the items haven't been requested
        mListingAdapter = new ListingAdapter(new ArrayList<ListingItem>());
        listingRecyclerView.setAdapter(mListingAdapter);
        listingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final String id= getIntent().getStringExtra(ID_ID);
        final String token= getIntent().getStringExtra(TOKEN_ID);
        retrieveListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retriveListing(id,token);
            }
        });

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

    }

    /**
     * Retrieve listing
     * @param id id for api call
     * @param token token for api call
     */
    private void retriveListing(String id, String token){
        showProgress(true);

        Call<ListingResponse> call = getApiServiceInstance().retrieveListing(id,token);
        call.enqueue(new Callback<ListingResponse>() {
            @Override
            public void onResponse(Call<ListingResponse> call, Response<ListingResponse> response) {
                showProgress(false);

                ListingResponse listingResponse = response.body();
                Status responseStatus = listingResponse.getStatus();

                if(response.isSuccessful() && responseStatus.isSuccessful()){
                    List<ListingItem> listing = mListingAdapter.getListing();
                    listing.clear();
                    listing.addAll(listingResponse.getListing());
                    mListingAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(MainActivity.this,getString(R.string.error_invalid_token),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ListingResponse> call, Throwable t) {
                showProgress(false);
                Toast.makeText(MainActivity.this,getString(R.string.error_network_issue),Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Sign out from the ongoing login session
     */
    private void signOut(){
        LoginSharedPreference.endLoginSession(this);
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }


    /**
     * Show the progress UI.
     */
    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
