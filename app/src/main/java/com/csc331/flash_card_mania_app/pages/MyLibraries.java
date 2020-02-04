package com.csc331.flash_card_mania_app.pages;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Space;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.components.LibraryListing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyLibraries extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final MyLibraries instance = this;

        setContentView(R.layout.my_libraries);
        getSupportActionBar().hide();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        Log.d("cDebug","Count: " + mainInstance.getLibraries().size());

        HashMap<String,Library> libraries = mainInstance.getLibraries();
        ArrayList<LibraryListing> listings = new ArrayList<>();
        LinearLayout layout = findViewById(R.id.library_layout);

        Log.d("cDebug","Parent width: " + layout.getMeasuredWidth());

        for (Map.Entry<String,Library> entry : libraries.entrySet()) {
            Library library = entry.getValue();

            LibraryListing listing = new LibraryListing(this,layout);
            listing.setName(library.getName());
            listing.setSubject(library.getSubject());
            listing.setCardCount(library.getCards().size() + " cards");
            layout.addView(listing.getPanel());
            listings.add(listing);

            final LibraryListing test = listing;
            listing.getPanel().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onGlobalLayout() {
                    test.getPanel().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    Log.d("cDebug","Outer-panel Width: " + test.getPanel().getWidth());
                    Log.d("cDebug","Inner-panel Width: " + test.getInnerPanel().getWidth());
                }
            });

            Space space = new Space(this);
            space.setMinimumWidth(layout.getWidth());
            space.setMinimumHeight(20);
            layout.addView(space);
        }
        Space space = new Space(this);
        space.setMinimumWidth(layout.getWidth());
        space.setMinimumHeight(135);
        layout.addView(space);

        final View layoutt = layout;
        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                layoutt.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                Log.d("cDebug","Layout Width: " + layoutt.getWidth());
            }
        });
        findViewById(R.id.mylibraries_back_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instance, MainMenu.class);

                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        for (LibraryListing item : listings) {
            final LibraryListing listing = item;

            listing.getPanel().findViewById(R.id.mylibraries_listing_edit_button).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("cDebug","Clicked " + listing.getName());
                }
            });
        }
    }
}
