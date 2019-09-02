package com.droidoxy.easymoneyrewards.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.droidoxy.easymoneyrewards.R;
import com.droidoxy.easymoneyrewards.activities.FragmentsActivity;
import com.droidoxy.easymoneyrewards.adapters.TransactionsAdapter;
import com.droidoxy.easymoneyrewards.app.App;
import com.droidoxy.easymoneyrewards.model.Transactions;
import com.droidoxy.easymoneyrewards.utils.CustomRequest;
import com.droidoxy.easymoneyrewards.utils.Dialogs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.droidoxy.easymoneyrewards.constants.Constants.ACCOUNT_TRANSACTIONS;
import static com.droidoxy.easymoneyrewards.constants.Constants.DEBUG_MODE;

/**
 * Created by DroidOXY
 */
 
public class TransactionsFragment extends Fragment {

    TextView emptyText;
    ImageView emptyImage;
    RecyclerView recentTransactions;
    TransactionsAdapter transactionsAdapter;
    ArrayList<Transactions> alltransactions;
    ProgressBar progressBar;

    public TransactionsFragment() {
        // Required empty public constructor
    }

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_transactions, container, false);

        emptyText = view.findViewById(R.id.empty);
        emptyImage = view.findViewById(R.id.emptyImage);
        progressBar = view.findViewById(R.id.progressBar);

        recentTransactions = view.findViewById(R.id.recentTransactions);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recentTransactions.setLayoutManager(layoutManager);
        recentTransactions.setItemAnimator(new DefaultItemAnimator());

        alltransactions = new ArrayList<>();

        transactionsAdapter = new TransactionsAdapter(getContext(),alltransactions);
        recentTransactions.setAdapter(transactionsAdapter);

        CustomRequest transactionsRequest = new CustomRequest(Request.Method.POST, ACCOUNT_TRANSACTIONS,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try{

                            JSONObject Response = new JSONObject(App.getInstance().deData(response.toString()));

                            if (!Response.getBoolean("error")) {

                                JSONArray transactions = Response.getJSONArray("transactions");

                                for (int i = 0; i < transactions.length(); i++) {

                                    JSONObject obj = transactions.getJSONObject(i);

                                    Transactions singleTN = new Transactions();

                                    singleTN.setTnId(obj.getString("tn_id"));
                                    singleTN.setTnName(obj.getString("tn_name"));
                                    singleTN.setTnType(obj.getString("tn_type"));
                                    singleTN.setStatus(obj.getString("tn_status"));
                                    singleTN.setTnDate(obj.getString("tn_date"));
                                    singleTN.setAmount(obj.getString("tn_points"));

                                    alltransactions.add(singleTN);
                                    progressBar.setVisibility(View.GONE);

                                }

                                transactionsAdapter.notifyDataSetChanged();

                                checkHaveTransactions();

                            }else if(Response.getInt("error_code") == 699 || Response.getInt("error_code") == 999){

                                Dialogs.validationError(getContext(),Response.getInt("error_code"));

                            }else{
                                if(!DEBUG_MODE){
                                    Dialogs.serverError(getContext(), getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            finish();
                                        }
                                    });
                                }
                            }

                        }catch (Exception e){
                            if(!DEBUG_MODE){
                                Dialogs.serverError(getContext(), getResources().getString(R.string.ok), new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        finish();
                                    }
                                });
                            }else{
                                Dialogs.errorDialog(getContext(),"Got Error",e.toString() + ", please contact developer immediately",true,false,"","ok",null);
                            }
                        }

                    }},new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

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
                Map<String,String> params = new HashMap<>();
                params.put("data", App.getInstance().getData());
                return params;
            }
        };

        App.getInstance().addToRequestQueue(transactionsRequest);

        return view;
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

    void checkHaveTransactions(){

        if(progressBar.getVisibility() == View.VISIBLE){

            emptyText.setText(getString(R.string.no_transactions));
            emptyText.setVisibility(View.VISIBLE);
            // emptyImage.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    void finish(){

        Activity close = getActivity();
        if(close instanceof FragmentsActivity){
            FragmentsActivity show = (FragmentsActivity) close;
            show.closeActivity();
        }

    }
}