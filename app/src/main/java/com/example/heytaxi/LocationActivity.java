package com.example.heytaxi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);


    }
    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            if(extras.getBoolean("hire") == true) {
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
        startActivity(intent);
    }

    public void onAvatar(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void onLogout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onStart(View view) {
        Intent intent = new Intent(this, RideActivity.class);
        startActivity(intent);
        finish();
    }
}
