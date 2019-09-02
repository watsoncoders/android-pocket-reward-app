package com.droidoxy.easymoneyrewards.activities;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.droidoxy.easymoneyrewards.R;
import com.droidoxy.easymoneyrewards.app.App;
import com.droidoxy.easymoneyrewards.utils.CustomRequest;
import com.droidoxy.easymoneyrewards.utils.Dialogs;
import com.droidoxy.easymoneyrewards.utils.Helper;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.OptionalPendingResult;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ResultCallback;

import static com.droidoxy.easymoneyrewards.constants.Constants.DEBUG_MODE;
import static android.provider.ContactsContract.Intents.Insert.EMAIL;
import static com.droidoxy.easymoneyrewards.Config.ENABLE_EMAIL_LOGIN;
import static com.droidoxy.easymoneyrewards.Config.ENABLE_FACEBOOK_LOGIN;
import static com.droidoxy.easymoneyrewards.Config.ENABLE_GMAIL_LOGIN;

/**
 * Created by DroidOXY
 */
 
public class SignupActivity extends ActivityBase implements GoogleApiClient.OnConnectionFailedListener{


    EditText signupUsername, signupFullname, signupPassword, signupEmail;
    Button signupJoinBtn;
    private static final int RC_SIGN_IN = 007;

    private GoogleApiClient mGoogleApiClient;

    private SignInButton btnSignIn;

    private CallbackManager mCallbackManager;

    private String username, fullname, password, email, user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

         signupUsername = (EditText) findViewById(R.id.signupUsername);
         signupFullname = (EditText) findViewById(R.id.signupFullname);
         signupPassword = (EditText) findViewById(R.id.signupPassword);
         signupEmail = (EditText) findViewById(R.id.signupEmail);

         signupJoinBtn = (Button) findViewById(R.id.signupJoinBtn);

        if(!ENABLE_EMAIL_LOGIN) {

            signupUsername.setVisibility(View.GONE);
            signupFullname.setVisibility(View.GONE);
            signupPassword.setVisibility(View.GONE);
            signupEmail.setVisibility(View.GONE);
            signupJoinBtn.setVisibility(View.GONE);
        }

        App.getInstance().getCountryCode();

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

        if(DEBUG_MODE){Log.e("Profile", "Malformed JSON: \"" + response.toString() + "\"");}

        if (App.getInstance().authorize(response)) {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

        ActivityCompat.finishAffinity(SignupActivity.this);

        } else if(App.getInstance().getErrorCode() == 699 || App.getInstance().getErrorCode() == 999){

            Dialogs.validationError(SignupActivity.this,App.getInstance().getErrorCode());

        } else {

        switch (App.getInstance().getErrorCode()) {

        case ERROR_LOGIN_TAKEN : {

        signupUsername.setError(getString(R.string.error_login_taken));
        break;
        }

        case ERROR_EMAIL_TAKEN : {

        signupEmail.setError(getString(R.string.error_email_taken));

        break;
        }

        case ERROR_IP_TAKEN : {

            Dialogs.warningDialog(SignupActivity.this,getResources().getString(R.string.error_device_exists),getResources().getString(R.string.error_device_exists_description),true,false,"",getResources().getString(R.string.ok),null);

            break;
        }

        default: {

            if(DEBUG_MODE){Log.e("Profile", "Could not parse malformed JSON: \"" + response.toString() + "\"");}

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
        if(DEBUG_MODE){Log.e("Profile", "Malformed JSON: \"" + error.getMessage() + "\"");}

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


        btnSignIn = (SignInButton) findViewById(R.id.btn_sign_in);
        if(!ENABLE_GMAIL_LOGIN) {

            btnSignIn.setVisibility(View.GONE);

        }

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

        // facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        mCallbackManager = CallbackManager.Factory.create();

        LoginButton mLoginButton = findViewById(R.id.login_button);
        if(!ENABLE_FACEBOOK_LOGIN){
            mLoginButton.setVisibility(View.GONE);
        }

        mLoginButton.setReadPermissions(Arrays.asList(EMAIL));

        // Register a callback to respond to the user
        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                setResult(RESULT_OK);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject jsonObject,GraphResponse response) {
                                try {

                                    String name = jsonObject.getString("name");
                                    String email =  jsonObject.getString("email");
                                    String id =  jsonObject.getString("id");

                                    signup_auto(name,email,getUsername(email));

                                } catch(JSONException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

                // finish();
            }

            @Override
            public void onCancel() {
                setResult(RESULT_CANCELED);

                Toast.makeText(getApplicationContext(), "user cancelled", Toast.LENGTH_LONG).show();


                // finish();
            }

            @Override
            public void onError(FacebookException e) {
                // Handle exception

                Toast.makeText(getApplicationContext(), "some error : " + e, Toast.LENGTH_LONG).show();

            }
        });
    }

    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            if(DEBUG_MODE){Log.e(TAG, "display name: " + acct.getDisplayName());}

            String personName = acct.getDisplayName();
            //String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            signup_auto(personName,email,getUsername(email));


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
        }else{
            mCallbackManager.onActivityResult(requestCode, resultCode, data);

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            if(DEBUG_MODE){Log.d(TAG, "Got cached sign-in");}
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showpDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hidepDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        if(DEBUG_MODE){Log.d(TAG, "onConnectionFailed:" + connectionResult);}
    }

    String getUsername(String email){

        String user_name_text = email.substring(0,email.lastIndexOf("@"));

        if(user_name_text.contains(".")){
            user_name = user_name_text.replace(".", "");
        }else{
            user_name = user_name_text;
        }

        return user_name;
    }

    void signup_auto(final String name, final String emailg, final String user_name){
        if (App.getInstance().isConnected()) {

            showpDialog();

            CustomRequest jsonReq = new CustomRequest(Request.Method.POST, METHOD_ACCOUNT_SIGNUP, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            if(DEBUG_MODE){Log.e("Profile", "Malformed JSON: \"" + response.toString() + "\"");}

                            if (App.getInstance().authorize(response)) {

                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);

                                ActivityCompat.finishAffinity(SignupActivity.this);

                            } else if(App.getInstance().getErrorCode() == 699 || App.getInstance().getErrorCode() == 999){

                                Dialogs.validationError(SignupActivity.this,App.getInstance().getErrorCode());

                            } else {

                                switch (App.getInstance().getErrorCode()) {

                                    case ERROR_LOGIN_TAKEN : {

                                Toast.makeText(getApplicationContext(), R.string.error_login_taken, Toast.LENGTH_LONG).show();
                                        Toast.makeText(getApplicationContext(), R.string.please_login, Toast.LENGTH_SHORT).show();
                                        finish();

                                        break;
                                    }

                                    case ERROR_EMAIL_TAKEN : {

                                        Toast.makeText(getApplicationContext(), R.string.error_email_taken, Toast.LENGTH_LONG).show();
                                        Toast.makeText(getApplicationContext(), R.string.please_login, Toast.LENGTH_SHORT).show();
                                        finish();

                                        break;
                                    }
                                    case ERROR_IP_TAKEN : {

                                        Dialogs.warningDialog(SignupActivity.this,getResources().getString(R.string.error_device_exists),getResources().getString(R.string.error_device_exists_description),true,false,"",getResources().getString(R.string.ok),null);

                                        break;
                                    }

                                    default: {

                                        if(DEBUG_MODE){Log.e("Profile", "Could not parse malformed JSON: \"" + response.toString() + "\"");}
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
                    if(DEBUG_MODE){Log.e("Profile", "Malformed JSON: \"" + error.getMessage() + "\"");}

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
