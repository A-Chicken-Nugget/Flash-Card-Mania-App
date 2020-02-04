package com.csc331.flash_card_mania_app.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csc331.flash_card_mania_app.R;

public class LibraryListing {
    private View panel;

    public LibraryListing(Context context, LinearLayout layout) {
        panel = LayoutInflater.from(context).inflate(R.layout.library_listing, layout, false);
    }

    public View getPanel() {
        return panel;
    }
    public View getInnerPanel() {
        return panel.findViewById(R.id.test_constraint);
    }
    public String getName() {
        return ((TextView)panel.findViewById(R.id.library_name)).getText().toString();
    }
    public void setName(String name) {
        ((TextView)panel.findViewById(R.id.library_name)).setText(name);
    }
    public void setSubject(String subject) {
        ((TextView)panel.findViewById(R.id.library_subject)).setText(subject);
    }
    public void setCardCount(String count) {
        ((TextView)panel.findViewById(R.id.library_card_count)).setText(count);
    }
}
