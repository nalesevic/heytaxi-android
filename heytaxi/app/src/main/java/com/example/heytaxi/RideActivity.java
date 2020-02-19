package com.example.heytaxi;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Timer;
import java.util.TimerTask;

import static com.android.volley.Request.Method.POST;

public class RideActivity extends AppCompatActivity{

    private int userID;
    private String driverID;
    private ViewPager main_pager;
    private String rating;
    private String review;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            userID = extras.getInt("userID");
            int driverIDint = extras.getInt("driverID");
            driverID = String.valueOf(driverIDint);
        }

        main_pager = (ViewPager) findViewById(R.id.main_pager);
        setupViewPager(main_pager);
    }

    private void setupViewPager(ViewPager pager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new RideFragment());
        adapter.addFragment(new RateFragment());
        pager.setAdapter(adapter);
    }

    public void onFinish(View view) {
        Fragment f = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.main_pager + ":" + 1);
        RateFragment rf = (RateFragment) f;
        review = rf.reviewET.getText().toString();
        float ratF = rf.ratingBar.getRating() * 3;
        rating = String.valueOf(ratF);

        RequestQueue requestQueue = Volley.newRequestQueue(RideActivity.this);
        String url = "http://35.229.27.81/rest/passenger/rate";
        StringRequest stringRequest = new StringRequest(POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("LOGIN RESPONSE", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String userType = jsonResponse.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
                params.put("rating", rating);
                Log.e("SENDING", driverID + " " + rating);
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

        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

}
