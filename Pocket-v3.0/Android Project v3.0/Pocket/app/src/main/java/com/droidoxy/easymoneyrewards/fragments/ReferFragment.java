package com.droidoxy.easymoneyrewards.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.droidoxy.easymoneyrewards.R;
import com.droidoxy.easymoneyrewards.activities.FragmentsActivity;
import com.droidoxy.easymoneyrewards.app.App;
import com.droidoxy.easymoneyrewards.utils.AppUtils;
import com.droidoxy.easymoneyrewards.utils.CustomRequest;
import com.droidoxy.easymoneyrewards.utils.Dialogs;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.droidoxy.easymoneyrewards.constants.Constants.ACCOUNT_REFER;
import static com.droidoxy.easymoneyrewards.constants.Constants.DEBUG_MODE;
import static com.droidoxy.easymoneyrewards.constants.Constants.ERROR_SUCCESS;

/**
 * Created by DroidOXY
 */
 
public class ReferFragment extends Fragment {

    TextView referTitle, referDescription, referCode, tapToCopy, referCodeTitle;
    Button referButton,submitReferCodeEntry;
    EditText referCodeEntry;
    ProgressDialog progressDialog ;
    LinearLayout inviteLayout, referCodeLayout;

    Context ctx;

    public ReferFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = getActivity();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_refer, container, false);

        referCode = view.findViewById(R.id.referCode);
        tapToCopy = view.findViewById(R.id.tapToCopy);
        referTitle = view.findViewById(R.id.referTitle);
        referCodeTitle = view.findViewById(R.id.referCodeTitle);
        referDescription = view.findViewById(R.id.referDescription);

        referButton = view.findViewById(R.id.referButton);
        submitReferCodeEntry = view.findViewById(R.id.submitReferCodeEntry);

        referCodeEntry = view.findViewById(R.id.referCodeEntry);
        inviteLayout = view.findViewById(R.id.inviteLayout);
        referCodeLayout = view.findViewById(R.id.referCodeLayout);

        referCode.setText(App.getInstance().get("refer_code",""));

        if(App.getInstance().get("refer_status",true)){ inviteLayout.setVisibility(View.GONE); }

        referDescription.setText(getResources().getString(R.string.referDescription) + " " + App.getInstance().get("REFER_REWARD","") + " " + getResources().getString(R.string.app_currency));

        referCodeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppUtils.copytoClipBoard(ctx, App.getInstance().get("refer_code",""));
                AppUtils.toastShort(ctx, getResources().getString(R.string.refer_code_copied));

            }
        });

        referButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refer();
            }
        });

        submitReferCodeEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String RefererId = referCodeEntry.getText().toString();
                if(!RefererId.isEmpty()){

                    if(RefererId.equals(App.getInstance().get("refer_code",""))){

                        Dialogs.errorDialog(ctx,getResources().getString(R.string.self_refer),getResources().getString(R.string.enter_valid_code),true,false,"",getResources().getString(R.string.ok),null);

                    }else if(RefererId.length() < 6){

                        Dialogs.errorDialog(ctx,getResources().getString(R.string.invalid_code),getResources().getString(R.string.enter_valid_code),true,false,"",getResources().getString(R.string.ok),null);

                    }else{

                        progressDialog = ProgressDialog.show(ctx,getResources().getString(R.string.processing),getResources().getString(R.string.please_wait),false,false);

                        addReferer(RefererId);
                    }

                }else{

                    Dialogs.errorDialog(ctx,getResources().getString(R.string.invalid_code),getResources().getString(R.string.enter_valid_code),true,false,"",getResources().getString(R.string.ok),null);
                }

            }
        });

        return view;
    }

    void refer(){
        try
        { Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String sAux = getResources().getString(R.string.get)+" "+ App.getInstance().get("REFER_REWARD","")+" "+getResources().getString(R.string.app_currency)+" "+getResources().getString(R.string.refer_text)+" "+App.getInstance().get("refer_code","")+"\n\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id="+ctx.getPackageName()+"\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            startActivity(Intent.createChooser(i, getString(R.string.choose_one)));
        }
        catch(Exception e) { //e.toString();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    void addReferer(final String RefererCode){

        CustomRequest referRequest = new CustomRequest(Request.Method.POST, ACCOUNT_REFER,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        progressDialog.dismiss();

                        try{

                            JSONObject Response = new JSONObject(App.getInstance().deData(response.toString()));

                            if(!Response.getBoolean("error") && Response.getInt("error_code") == ERROR_SUCCESS){

                                // Refer Success
                                App.getInstance().store("refer_status",true);
                                Dialogs.succesDialog(ctx, getResources().getString(R.string.congratulations), App.getInstance().get("REFER_REWARD","") + " " + getResources().getString(R.string.app_currency) + " " + getResources().getString(R.string.invitation_bonus_received), false, false, "", getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                                        inviteLayout.setVisibility(View.GONE);
                                        sweetAlertDialog.dismissWithAnimation();

                                    }
                                });

                            }else if(Response.getInt("error_code") == 404) {

                                // 404 - Invalid Referer Code
                                Dialogs.errorDialog(ctx, getResources().getString(R.string.invalid_code), getResources().getString(R.string.enter_valid_code), false, false, "", getResources().getString(R.string.ok), null);

                            }else if(Response.getInt("error_code") == 420) {

                                // 420 - Self Refer
                                Dialogs.errorDialog(ctx, getResources().getString(R.string.self_refer), getResources().getString(R.string.enter_valid_code), false, false, "", getResources().getString(R.string.ok), null);

                            }else if(Response.getInt("error_code") == 699 || Response.getInt("error_code") == 999){

                                Dialogs.validationError(ctx,Response.getInt("error_code"));

                            }else if(Response.getInt("error_code") == 400) {

                                // Refer Bonus Taken Already
                                App.getInstance().store("refer_status",true);
                                Dialogs.errorDialog(ctx, "", getResources().getString(R.string.bonus_taken), false, false, "", getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        inviteLayout.setVisibility(View.GONE);
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                });

                            }else if(DEBUG_MODE){

                                // For Testing ONLY - intended for Developer Use ONLY not visible for Normal App user
                                Dialogs.errorDialog(ctx,Response.getString("error_code"),Response.getString("error_description"),false,false,"",getResources().getString(R.string.ok),null);

                            }else{

                                // Server error
                                Dialogs.serverError(ctx, getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        finish();
                                    }
                                });
                            }

                        }catch (Exception e){

                            if(!DEBUG_MODE){
                                Dialogs.serverError(ctx, getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        finish();
                                    }
                                });
                            }else{
                                Dialogs.errorDialog(ctx,"Got Error",e.toString() + ", please contact developer immediately",false,false,"","ok",null);
                            }

                        }

                    }},new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

                if(!DEBUG_MODE){
                    Dialogs.serverError(getContext(), getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            finish();
                        }
                    });
                }else{
                    Dialogs.errorDialog(getContext(),"Got Error",error.toString(),true,false,"","ok",null);
                }

            }}){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("data", App.getInstance().getDataCustom("refer",RefererCode));
                return params;
            }
        };

        App.getInstance().addToRequestQueue(referRequest);

    }

    void finish(){

        Activity close = getActivity();
        if(close instanceof FragmentsActivity){
            FragmentsActivity show = (FragmentsActivity) close;
            show.closeActivity();
        }

    }

}