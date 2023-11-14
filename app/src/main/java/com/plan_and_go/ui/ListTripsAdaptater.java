package com.plan_and_go.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.plan_and_go.R;
import com.plan_and_go.model.Trip;

import java.util.ArrayList;

public class ListTripsAdaptater extends ArrayAdapter<Trip> {
    public ListTripsAdaptater(@NonNull Context context, ArrayList<Trip> tripArrayList) {
        super(context, R.layout.list_item, tripArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        return view;
    }
}
