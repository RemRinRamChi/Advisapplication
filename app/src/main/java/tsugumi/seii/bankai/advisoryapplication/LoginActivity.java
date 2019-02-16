package tsugumi.seii.bankai.advisoryapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tsugumi.seii.bankai.advisoryapplication.model.LoginResponse;
import tsugumi.seii.bankai.advisoryapplication.model.Status;

import static tsugumi.seii.bankai.advisoryapplication.Utility.getApiServiceInstance;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress_page);

        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        // Listen to user's input method for log in event
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        if(LoginSharedPreference.loginSessionIsOngoing(this)){
            logIn(LoginSharedPreference.getEmail(this),LoginSharedPreference.getPassword(this));
        }

    }

    /**
     * Logs into the service to retireve id and token
     * @param email user email
     * @param password user password
     */
    private void logIn(final String email, final String password){
        showProgress(true);

        Call<LoginResponse> call = getApiServiceInstance().login(email,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                Status responseStatus = loginResponse.getStatus();

                if(response.isSuccessful() && responseStatus.isSuccessful()){
                    LoginSharedPreference.persistLoginSession(LoginActivity.this,email,password);
                    goToMainActivity(loginResponse.getId(), loginResponse.getToken());
                } else {
                    showProgress(false);
                    mEmailView.setError(getString(R.string.error_incorrect_email));
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                    mPasswordView.requestFocus();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                showProgress(false);
                Toast.makeText(LoginActivity.this,"Please ensure that you are connected to the internet.",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goToMainActivity(String id, String token){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(MainActivity.ID_ID,id);
        intent.putExtra(MainActivity.TOKEN_ID,token);
        startActivity(intent);
        finish();
    }

    /**
     * Attempts to sign in the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            logIn(email,password);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}

