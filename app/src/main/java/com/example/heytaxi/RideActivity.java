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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

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
        float ratF = rf.ratingBar.getRating();
        rating = String.valueOf(ratF);
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        Intent intent = new Intent(RideActivity.this, LocationActivity.class);
                        intent.putExtra("hire", false);
                        intent.putExtra("userID", userID);
                        RideActivity.this.startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RideActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        ReviewRequest reviewRequest = new ReviewRequest(driverID, review, rating, responseListener);
        RequestQueue queue = Volley.newRequestQueue(RideActivity.this);
        queue.add(reviewRequest);
    }

}
