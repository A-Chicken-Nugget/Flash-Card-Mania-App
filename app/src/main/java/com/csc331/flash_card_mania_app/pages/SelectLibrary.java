package com.csc331.flash_card_mania_app.pages;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.components.LibraryListing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectLibrary extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SelectLibrary instance = this;

        final Boolean testType = getIntent().getBooleanExtra("testType",false);

        //Set this pages view layout
        setContentView(R.layout.select_library);
        //Remove the default app header
        getSupportActionBar().hide();
        //Remove the device notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        HashMap<UUID,Library> libraries = mainInstance.getLibraries();
        LinearLayout layout = findViewById(R.id.selectLibrary_libraryLayout);

        //Layout the libraries and create their listing instances
        for (Map.Entry<UUID,Library> entry : libraries.entrySet()) {
            Library library = entry.getValue();

            final LibraryListing listing = new LibraryListing(this,layout,library,false);
            listing.setName(library.getName());
            listing.setSubject(library.getSubject());
            listing.setCardCount(library.getCards().size() + " cards");
            layout.addView(listing.getPanel());

            //Handle when each listing is clicked
            listing.getPanel().findViewById(R.id.libraryListing_bodyConstraint).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(instance, SelectSettings.class);

                    //Attach the listings library id and test type
                    intent.putExtra("libraryId",String.valueOf(listing.getLibrary().getID()));
                    intent.putExtra("testType",testType);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
            });

            Space space = new Space(this);
            space.setMinimumWidth(layout.getWidth());
            space.setMinimumHeight(20);
            layout.addView(space);
        }

        //Handle when the back button is clicked
        findViewById(R.id.selectlibrary_back_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(instance, MainMenu.class));
                overridePendingTransition(0,0);
            }
        });
    }
}