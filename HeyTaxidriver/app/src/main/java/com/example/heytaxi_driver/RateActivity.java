package com.example.heytaxi_driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RateActivity extends AppCompatActivity {

    private String driverID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            driverID = extras.getString("driverID");
        }
    }

    public void onFinish(View view) {
        Intent intent = new Intent(this, DriverUIActivity.class);
        intent.putExtra("driverID", driverID);
        startActivity(intent);
        finish();
    }
}
