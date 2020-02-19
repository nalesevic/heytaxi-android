package com.example.heytaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferDetailsActivity extends AppCompatActivity {

    private int userID;
    private int driverID;
    private TextView firstNameTV;
    private TextView lastNameTV;
    private TextView vehicleTypeTV;
    private TextView companyTV;
    private RatingBar ratingBar;
    private Integer rideID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        firstNameTV = findViewById(R.id.driver_firstName);
        lastNameTV = findViewById(R.id.driver_lastName);
        vehicleTypeTV = findViewById(R.id.driver_vehicle);
        companyTV = findViewById(R.id.driver_company);
        ratingBar = findViewById(R.id.driver_rating);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            userID = extras.getInt("userID");
            driverID  = extras.getInt("driverID");
            String driverfn = extras.getString("driverfn");
            String driverln = extras.getString("driverln");
            String vehicleType = extras.getString("vt");
            String company = extras.getString("c");
            String driverLocation = extras.getString("dl");
            String rating = extras.getString("r");
            Log.e("OFFERDETAILS", rating);
            firstNameTV.setText(driverfn);
            lastNameTV.setText(driverln);
            vehicleTypeTV.setText(vehicleType);
            companyTV.setText(company);
            // since stars go from 1-5 and in base it is from 0-100
            ratingBar.setRating(Float.parseFloat(rating) / 20);
        }
    }

    public void onHire(View view) {
        String url = "http://35.229.27.81/rest/passenger/book/driver";
        RequestQueue requestQueue = Volley.newRequestQueue(OfferDetailsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                rideID = Integer.valueOf(response);
                Log.e("RESPONSE", response);
                Intent intent = new Intent(OfferDetailsActivity.this, TaxiWaitActivity.class);
                intent.putExtra("userID", userID);
                intent.putExtra("rideID", rideID);
                intent.putExtra("driverID", driverID);
                Log.e("RIDE ID", "U offeru " + rideID);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("HTTP ERROR", "Error while booking selected driver");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("driverID", String.valueOf(driverID));
                Log.e("DRIVER ID KAD SALJEM", String.valueOf(driverID));
                params.put("passengerID", String.valueOf(userID));
                User user = HTDatabase.getDatabase(OfferDetailsActivity.this).userDAO().getUserById(userID);
                params.put("startPointLatitude", String.valueOf(user.getLatitude()));
                params.put("startPointLongitude", String.valueOf(user.getLongitude()));
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);


    }

}
