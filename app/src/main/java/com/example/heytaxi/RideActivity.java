package com.example.heytaxi;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class RideActivity extends AppCompatActivity {

    private int userID;
    private ViewPager main_pager;
    private TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            userID = extras.getInt("userID");
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
        Intent intent = new Intent(this, LocationActivity.class);
        intent.putExtra("hire", false);
        intent.putExtra("userID", userID);
        startActivity(intent);
        finish();
    }

}
