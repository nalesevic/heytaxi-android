package com.example.heytaxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

public class RateFragment extends Fragment{

    public RatingBar ratingBar;
    public EditText reviewET;
    private static String rating;
    private static String review;

    public String getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rate, container, false);
        ratingBar = (RatingBar) view.findViewById(R.id.ratingBar_onDriver);
        reviewET = (EditText) view.findViewById(R.id.review_onDriver);
        review = reviewET.getText().toString();
        float ratingf = ratingBar.getRating();
        rating = String.valueOf(ratingf);
        return view;
    }

}
