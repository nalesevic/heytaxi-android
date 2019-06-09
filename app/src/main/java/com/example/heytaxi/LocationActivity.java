package com.example.heytaxi;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    private EditText userLocationET;
    private int userID;
    private boolean hire;
    private String userLocation;
    private String rating;
    private String vehicleType;
    private String company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        userLocationET = (EditText) findViewById(R.id.user_location);
        userLocation = userLocationET.getText().toString();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            userID = extras.getInt("userID");
            vehicleType = extras.getString("vehicleType");
            company = extras.getString("company");
            rating = extras.getString("rating");
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null) {
            userID = extras.getInt("userID");
            hire = extras.getBoolean("hire");
            // redundant ? ?
            vehicleType = extras.getString("vehicleType");
            company = extras.getString("company");
            rating = extras.getString("rating");
            //
            if(hire == true) {
                TextView hireInstr = (TextView) findViewById(R.id.notif);
                Button hire = (Button) findViewById(R.id.btnStart);
                hire.setVisibility(View.VISIBLE);
                hireInstr.setVisibility(View.VISIBLE);
            }
        }
    }

    public void onAddFilters(View view) {
        Intent intent = new Intent(this, FilterActivity.class);
        startActivity(intent);
    }

    public void onGetTaxi(View view) {
        Intent intent = new Intent(this, OfferActivity.class);
        userLocation = userLocationET.getText().toString();
        intent.putExtra("userID", userID);
        intent.putExtra("userLocation", userLocation);
        intent.putExtra("rating", rating);
        intent.putExtra("vehicleType", vehicleType);
        intent.putExtra("company", company);
        startActivity(intent);

    }

    public void onAvatar(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("hire", hire);
        intent.putExtra("userID", userID);
        startActivity(intent);
    }

    public void onLogout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        ActivityCompat.finishAffinity(this);
        startActivity(intent);
        finish();
    }

    public void onStart(View view) {
        Intent intent = new Intent(this, RideActivity.class);
        intent.putExtra("userID", userID);
        startActivity(intent);
        finish();
    }
}
