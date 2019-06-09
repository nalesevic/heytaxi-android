package com.example.heytaxi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DriversListAdapter extends BaseAdapter {
    private Context context;
    private List<Driver> drivers;

    public DriversListAdapter(Context context, List<Driver> drivers){
        this.context = context;
        this.drivers = drivers;
    }
    @Override
    public int getCount() {
        return drivers.size();
    }

    @Override
    public Object getItem(int position) {
        return drivers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return drivers.indexOf(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(R.layout.list_drivers, parent, false);

        TextView company = convertView.findViewById(R.id.company_name);
        TextView vehicle = convertView.findViewById(R.id.vehicle);

        Driver driver = drivers.get(position);

        company.setText(driver.getCompany());
        vehicle.setText(driver.getVehicle());

        return convertView;
    }
}

