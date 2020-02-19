package com.example.heytaxi;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaxiWaitActivity extends AppCompatActivity {

    private String taxiResponse;
    private Integer rideID;
    TextView tvTaxiResponse;
    private String ETA;
    private int driverID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxi_wait);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            rideID = extras.getInt("rideID");
            driverID = extras.getInt("driverID");
        }
        tvTaxiResponse = findViewById(R.id.tvTaxiWait);
        String url = "http://35.229.27.81/rest/passenger/book/wait";
        RequestQueue requestQueue = Volley.newRequestQueue(TaxiWaitActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RE", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    Log.e("STATUS", status);
                    if (status.equals("1")) {
                        String eta = jsonResponse.getString("ETA");
                        tvTaxiResponse.setText("Taxi will be there in " + eta + " minutes");
                    } else if (status.equals("2")) {
                        tvTaxiResponse.setText("Taxi refused your request");
                    } else {
                        tvTaxiResponse.setText("Please wait for response");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("rideID", String.valueOf(rideID));
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

    public void onRefresh(View view) {
        String url = "http://35.229.27.81/rest/passenger/book/wait";
        RequestQueue requestQueue = Volley.newRequestQueue(TaxiWaitActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("RE", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String status = jsonResponse.getString("status");
                    Log.e("STATUS", status);
                    if (status.equals("1")) {
                        String eta = jsonResponse.getString("ETA");
                        tvTaxiResponse.setText("Taxi will be there in " + eta + " minutes");
                    } else if (status.equals("2")) {
                        tvTaxiResponse.setText("Taxi refused your request");
                    } else {
                        tvTaxiResponse.setText("Please wait for response");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("rideID", " " + rideID);
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

    public void onStart(View view) {
        Intent intent = new Intent(TaxiWaitActivity.this, RideActivity.class);
        intent.putExtra("rideID", rideID);
        intent.putExtra("driverID", driverID);
        startActivity(intent);
        finish();
    }
}
