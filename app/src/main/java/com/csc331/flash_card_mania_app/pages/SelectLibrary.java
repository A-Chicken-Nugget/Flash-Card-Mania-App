package com.csc331.flash_card_mania_app.pages;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.components.LibraryListingOnly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectLibrary extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();
    private String randomized = "False";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SelectLibrary instance = this;

        //Set this pages view layout
        setContentView(R.layout.select_library);
        //Remove the default app header
        getSupportActionBar().hide();
        //Remove the device notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        Log.d("cDebug","Count: " + mainInstance.getLibraries().size());

        HashMap<UUID,Library> libraries = mainInstance.getLibraries();
        //Keep track of the library listing instances
        ArrayList<LibraryListingOnly> listings = new ArrayList<>();
        LinearLayout layout = findViewById(R.id.library_layout);

        findViewById(R.id.selectLibrary_Checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((CheckBox)v).isChecked()) {
                    randomized = "True";
                }
            }
        });

        //If the user has created libraries
        if (libraries.size() > 0) {
            //Layout the libraries and create their listing instances
            for (Map.Entry<UUID,Library> entry : libraries.entrySet()) {
                Library library = entry.getValue();

                final LibraryListingOnly listing = new LibraryListingOnly(this,layout,library);
                listing.setName(library.getName());
                listing.setSubject(library.getSubject());
                listing.setCardCount(library.getCards().size() + " cards");
                layout.addView(listing.getPanel());
                listings.add(listing);

                //Handle when each listing is clicked
                listing.getPanel().findViewById(R.id.libraryListingOnly_bodyConstraint).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(instance, Learn.class);

                        //Attach the listings library id
                        intent.putExtra("libraryId",String.valueOf(listing.getLibrary().getID()));
                        intent.putExtra("isRandom",randomized);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                    }
                });

                Space space = new Space(this);
                space.setMinimumWidth(layout.getWidth());
                space.setMinimumHeight(20);
                layout.addView(space);
            }
        } else {
            TextView text = new TextView(this);
            text.setText("No libraries have been created. Click the plus symbol to create one");
            text.setTextSize(18);
            text.setTextColor(Color.BLACK);
            text.setGravity(Gravity.CENTER);
            layout.addView(text);
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