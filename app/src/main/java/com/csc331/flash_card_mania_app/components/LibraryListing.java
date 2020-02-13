package com.csc331.flash_card_mania_app.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.R;

public class LibraryListing {
    private View panel;
    private Library library;

    public LibraryListing(Context context, LinearLayout layout, Library library) {
        panel = LayoutInflater.from(context).inflate(R.layout.library_listing, layout, false);
        this.library = library;
    }

    public View getPanel() {
        return panel;
    }
    public Library getLibrary() {
        return library;
    }
    public View getInnerPanel() {
        return panel.findViewById(R.id.libraryListing_bodyConstraint);
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
