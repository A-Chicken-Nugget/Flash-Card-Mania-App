package com.csc331.flash_card_mania_app.components;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.Main;


public class StatisticsListing {
    private View panel;
    private Library library;

    public StatisticsListing(Context context, LinearLayout layout, Library library) {
        panel = LayoutInflater.from(context).inflate(R.layout.statistics_listing, layout, false);
        this.library = library;
    }
    public StatisticsListing(Context context, LinearLayout layout) {
        panel = LayoutInflater.from(context).inflate(R.layout.statistics_listing, layout, false);
    }

    public View getPanel() {
        return panel;
    }
    public Library getLibrary() {
        return library;
    }
    public View getInnerPanel() {
        return panel.findViewById(R.id.statistics_listing_body_constraint);
    }
    public String getName() {
        return ((TextView)panel.findViewById(R.id.statistics_listing_name)).getText().toString();
    }
    public void setName(String name) {
        ((TextView)panel.findViewById(R.id.statistics_listing_name)).setText(name);
    }
    public void setTime(String time) {
        ((TextView)panel.findViewById(R.id.statistics_listing_time)).setText(time);
    }
    public void setDate(String date) {
        ((TextView)panel.findViewById(R.id.statistics_listing_date)).setText(date);
    }
}