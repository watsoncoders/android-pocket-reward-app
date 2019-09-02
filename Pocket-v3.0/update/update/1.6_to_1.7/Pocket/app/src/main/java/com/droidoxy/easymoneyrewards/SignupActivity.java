package com.droidoxy.easymoneyrewards;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import com.droidoxy.easymoneyrewards.app.App;
import com.droidoxy.easymoneyrewards.common.ActivityBase;
import com.droidoxy.easymoneyrewards.util.CustomRequest;
import com.droidoxy.easymoneyrewards.util.Helper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.OptionalPendingResult;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ResultCallback;

public class SignupActivity extends ActivityBase implements GoogleApiClient.OnConnectionFailedListener{


    EditText signupUsername, signupFullname, signupPassword, signupEmail;
    Button signupJoinBtn;
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;

    private SignInButton btnSignIn;
    private ProgressDialog mProgressDialog;


    private String username, fullname, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);

         signupUsername = (EditText) findViewById(R.id.signupUsername);
         signupFullname = (EditText) findViewById(R.id.signupFullname);
         signupPassword = (EditText) findViewById(R.id.signupPassword);
         signupEmail = (EditText) findViewById(R.id.signupEmail);

         signupJoinBtn = (Button) findViewById(R.id.signupJoinBtn);

         signupJoinBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        username = signupUsername.getText().toString();
        fullname = signupFullname.getText().toString();
        password = signupPassword.getText().toString();
        email = signupEmail.getText().toString();

        if (verifyRegForm()) {

        if (App.getInstance().isConnected()) {

        showpDialog();

        CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_ACCOUNT_SIGNUP, null,
        new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {

        Log.e("Profile", "Malformed JSON: \"" + response.toString() + "\"");

        if (App.getInstance().authorize(response)) {

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

        ActivityCompat.finishAffinity(SignupActivity.this);

        } else {

        switch (App.getInstance().getErrorCode()) {

        case 300 : {

        signupUsername.setError(getString(R.string.error_login_taken));
        break;
        }

        case 301 : {

        signupEmail.setError(getString(R.string.error_email_taken));
        break;
        }

        default: {

        Log.e("Profile", "Could not parse malformed JSON: \"" + response.toString() + "\"");
        break;
        }
        }
        }

        hidepDialog();
        }
        }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        Log.e("Profile", "Malformed JSON: \"" + error.getMessage() + "\"");

        hidepDialog();
        }
        }) {

        @Override
        protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("fullname", fullname);
        params.put("password", password);
        params.put("email", email);
        params.put("clientId", CLIENT_ID);

        return params;
        }
        };

        App.getInstance().addToRequestQueue(jsonReq);

        } else {

        Toast.makeText(getApplicationContext(), R.string.msg_network_error, Toast.LENGTH_SHORT).show();
        }
        }
        }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        // Customizing G+ button
        btnSignIn.setSize(SignInButton.SIZE_STANDARD);
        btnSignIn.setScopes(gso.getScopeArray());

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            //String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            signin_google(personName,email);

        } else {
            // Signed out, show unauthenticated UI.
            // show toast
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading..");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }


    void signin_google(final String name, final String emailg){
        if (App.getInstance().isConnected()) {

            showpDialog();

            final String user_name = emailg.substring(0,emailg.lastIndexOf("@"));

            CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_ACCOUNT_SIGNUP, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.e("Profile", "Malformed JSON: \"" + response.toString() + "\"");

                            if (App.getInstance().authorize(response)) {

                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);

                                ActivityCompat.finishAffinity(SignupActivity.this);

                            } else {

                                switch (App.getInstance().getErrorCode()) {

                                    case 300 : {

                                Toast.makeText(getApplicationContext(), R.string.error_login_taken, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "please login ....", Toast.LENGTH_SHORT).show();
                                        finish();

                                        break;
                                    }

                                    case 301 : {

                                        Toast.makeText(getApplicationContext(), R.string.error_email_taken, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(getApplicationContext(), "please login ....", Toast.LENGTH_SHORT).show();
                                        finish();

                                        break;
                                    }

                                    default: {

                                        Log.e("Profile", "Could not parse malformed JSON: \"" + response.toString() + "\"");
                                        break;
                                    }
                                }
                            }

                            hidepDialog();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("Profile", "Malformed JSON: \"" + error.getMessage() + "\"");

                    hidepDialog();
                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", user_name);
                    params.put("fullname", name);
                    params.put("password", user_name);
                    params.put("email", emailg);
                    params.put("clientId", CLIENT_ID);

                    return params;
                }
            };

            App.getInstance().addToRequestQueue(jsonReq);

        } else {

            Toast.makeText(getApplicationContext(), R.string.msg_network_error, Toast.LENGTH_SHORT).show();
        }
    }
    public Boolean verifyRegForm() {

        signupUsername.setError(null);
        signupFullname.setError(null);
        signupPassword.setError(null);
        signupEmail.setError(null);

        Helper helper = new Helper();

        if (username.length() == 0) {

            signupUsername.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (username.length() < 5) {

            signupUsername.setError(getString(R.string.error_small_username));

            return false;
        }

        if (!helper.isValidLogin(username)) {

            signupUsername.setError(getString(R.string.error_wrong_format));

            return false;
        }

        if (fullname.length() == 0) {

            signupFullname.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (password.length() == 0) {

            signupPassword.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (password.length() < 6) {

            signupPassword.setError(getString(R.string.error_small_password));

            return false;
        }

        if (!helper.isValidPassword(password)) {

            signupPassword.setError(getString(R.string.error_wrong_format));

            return false;
        }

        if (email.length() == 0) {

            signupEmail.setError(getString(R.string.error_field_empty));

            return false;
        }

        if (!helper.isValidEmail(email)) {

            signupEmail.setError(getString(R.string.error_wrong_format));

            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed(){

        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case android.R.id.home: {

                finish();
                return true;
            }

            default: {

                return super.onOptionsItemSelected(item);
            }
        }
    }
}
