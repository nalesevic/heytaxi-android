package com.example.heytaxi.ui.send;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.heytaxi.FilterActivity;
import com.example.heytaxi.LocationActivity;
import com.example.heytaxi.R;
import com.example.heytaxi.ui.home.HomeFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SendFragment extends Fragment {

    private Spinner companyS;
    private Spinner vehicleTypeS;
    private RatingBar ratingBar;
    private String company;
    private String vehicleType;
    private String rating;
    private Button btnApplyFilter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_send, container, false);
        companyS = root.findViewById(R.id.spinnerCompany);
        vehicleTypeS = root.findViewById(R.id.spinnerVehicleType);
        ratingBar = root.findViewById(R.id.starRating);
        btnApplyFilter = root.findViewById(R.id.btnApplyFilter);

        String url = "http://35.229.27.81/rest/companies";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    List<String> companies = new ArrayList<>();
                    JSONArray mJsonArray = new JSONArray(response);
                    JSONObject mJsonObject = new JSONObject();
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        mJsonObject = mJsonArray.getJSONObject(i);
                        company = mJsonObject.getString("companyName");
                        companies.add(company);
                    }
                    String[] vehicles = new String[] {
                            "all", "Sedan", "Hatchback", "Minivan", "SUV"
                    };
                    ArrayAdapter<String> companyAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item, companies);
                    companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_spinner_dropdown_item, vehicles);
                    companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    companyS.setAdapter(companyAdapter);
                    vehicleTypeS.setAdapter(vehicleAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HTTP ERROR", "Error while fetching companies");
            }
        });
        requestQueue.add(stringRequest);

        btnApplyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating_float = ratingBar.getRating();
                rating = String.valueOf(rating_float);
                company = companyS.getSelectedItem().toString();
                vehicleType = vehicleTypeS.getSelectedItem().toString();
                if(rating.equals("0.0"))
                    rating = "6";
                Bundle bundle = new Bundle();
                bundle.putString("rating", rating);
                bundle.putString("company", company);
                bundle.putString("vehicleType", vehicleType);
                HomeFragment homeFragment = new HomeFragment();
                homeFragment.setArguments(bundle);
                Log.e("POSLAO", "poslao");
            }
        });

        return root;
    }
}