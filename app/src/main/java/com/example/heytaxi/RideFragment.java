package com.example.heytaxi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RideFragment extends Fragment {

    private Handler handler;
    private int timerCount;
    private float distanceCount;
    private TextView timerText;
    private TextView distanceText;
    private String time;
    private String distance;

    public static RideFragment newInstance() {
        RideFragment fragment = new RideFragment();
        Bundle args = new Bundle();

        args.putString("time", "00:00");
        args.putString("distance", "0");
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ride, container, false);
        timerText = (TextView) view.findViewById(R.id.timer);
        distanceText = (TextView) view.findViewById(R.id.distance);
        timerCount=0;
        distanceCount = 0;
        handler=new Handler();

        Runnable r = new Runnable() {
            public void run() {
                timerCount++;
                distanceCount += 0.1;
                timerText.setText("00:"+timerCount);
                int d = Math.round(distanceCount);
                distanceText.setText(""+d);
                handler.postDelayed(this, 1000); //  delay one second before updating the number
            }
        };

        handler.postDelayed(r, 1000);
        return view;
    }

}
