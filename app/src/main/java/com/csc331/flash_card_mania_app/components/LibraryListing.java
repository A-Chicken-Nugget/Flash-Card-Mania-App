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

public class LibraryListing {
    private View panel;
    private Library library;

    public LibraryListing(Context context, LinearLayout layout, Library library, boolean includeButtons) {
        panel = LayoutInflater.from(context).inflate(R.layout.library_listing, layout, false);
        this.library = library;

        if (!includeButtons) {
            panel.findViewById(R.id.mylibraries_listing_edit_button).setVisibility(View.GONE);
            panel.findViewById(R.id.mylibraries_listing_delete_button).setVisibility(View.GONE);

            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams)panel.findViewById(R.id.libraryListing_dataLayout).getLayoutParams();
            layoutParams.rightMargin = 25;
        }
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
