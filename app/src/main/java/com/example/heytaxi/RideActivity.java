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

    private ViewPager main_pager;
    private TextView timerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);



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
        startActivity(intent);
        intent.putExtra("hire", false);
        finish();
    }

}
