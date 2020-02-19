package com.example.heytaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

    private int userID;
    private String company;
    private String vehicleType;
    private String rating;
    private Spinner companyS;
    private Spinner vehicleTypeS;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            userID = extras.getInt("userID");
        }

        companyS = findViewById(R.id.spinnerCompany);
        vehicleTypeS = findViewById(R.id.spinnerVehicleType);
        ratingBar = findViewById(R.id.starRating);

        String url = "http://35.229.27.81/rest/companies";
        RequestQueue requestQueue = Volley.newRequestQueue(FilterActivity.this);
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
                    ArrayAdapter<String> companyAdapter = new ArrayAdapter<String>(FilterActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, companies);
                    companyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ArrayAdapter<String> vehicleAdapter = new ArrayAdapter<String>(FilterActivity.this,
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
    }

    public void onApplyFilter(View view) {
        float rating_float = ratingBar.getRating() * 20;
        rating = String.valueOf(rating_float);
        company = companyS.getSelectedItem().toString();
        vehicleType = vehicleTypeS.getSelectedItem().toString();
        if(rating.equals("0.0"))
            rating = "100";
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra("userID", userID);
        intent.putExtra("rating", rating);
        intent.putExtra("company", company);
        intent.putExtra("vehicleType", vehicleType);
        startActivity(intent);
        finish();
    }
}
