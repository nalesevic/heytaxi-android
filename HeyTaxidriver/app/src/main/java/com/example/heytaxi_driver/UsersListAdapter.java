package com.example.heytaxi_driver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UsersListAdapter extends BaseAdapter {
    private Context context;
    private List<PassengerRequest> users;

    public UsersListAdapter(Context context, List<PassengerRequest> users){
        this.context = context;
        this.users = users;
    }
    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return users.indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_users, parent, false);

        TextView tvPassengerLocation = convertView.findViewById(R.id.passenger_location);
        PassengerRequest pr = users.get(position);
        tvPassengerLocation.setText(pr.getPassengerLocationAddress());

        return convertView;
    }

}
