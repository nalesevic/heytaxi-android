package com.example.heytaxi_driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
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

import static com.android.volley.Request.Method.POST;

public class TimeArrivalActivity extends AppCompatActivity {

    private String ETA;
    private String rideID;
    private String driverID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_arrival);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            rideID = extras.getString("rideID");
            driverID = extras.getString("driverID");
        }
    }

    public void onThree(View view) {
        ETA = "3";
    }
    public void onFive(View view) {
        ETA = "5";
    }
    public void onTen(View view) {
        ETA = "10";
    }

    public void onSend(View view) {
        RequestQueue requestQueue = Volley.newRequestQueue(TimeArrivalActivity.this);
        String url = "http://35.229.27.81/rest/driver/book/response";
        StringRequest stringRequest = new StringRequest(POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("ARRIVAL RESPONSE", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RESPONSE", String.valueOf(error));
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("driverID", driverID);
                params.put("rideID", rideID);
                params.put("ETA", ETA);
                Log.e("SENDING", ETA + " " + rideID + " " + driverID);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        requestQueue.add(stringRequest);

        Intent intent = new Intent(this, RateActivity.class);
        intent.putExtra("driverID", driverID);
        startActivity(intent);
        finish();
    }
}
