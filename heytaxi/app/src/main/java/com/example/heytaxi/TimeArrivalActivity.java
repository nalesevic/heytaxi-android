package com.example.heytaxi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TimeArrivalActivity extends AppCompatActivity {

    private int ETA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_arrival);
    }

    public void onThree(View view) {
        ETA = 3;
    }
    public void onFive(View view) {
        ETA = 5;
    }
    public void onTen(View view) {
        ETA = 10;
    }

    public void onSend(View view) {

    }
}
