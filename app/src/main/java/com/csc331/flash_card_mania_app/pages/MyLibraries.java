package com.csc331.flash_card_mania_app.pages;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.components.LibraryListing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyLibraries extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final MyLibraries instance = this;

        //Set this pages view layout
        setContentView(R.layout.my_libraries);
        //Remove the default app header
        getSupportActionBar().hide();
        //Remove the device notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        Log.d("cDebug","Count: " + mainInstance.getLibraries().size());

        HashMap<UUID,Library> libraries = mainInstance.getLibraries();
        //Keep track of the library listing instances
        ArrayList<LibraryListing> listings = new ArrayList<>();
        LinearLayout layout = findViewById(R.id.library_layout);

        //If the user has created libraries
        if (libraries.size() > 0) {
            //Layout the libraries and create their listing instances
            for (Map.Entry<UUID,Library> entry : libraries.entrySet()) {
                Library library = entry.getValue();

                LibraryListing listing = new LibraryListing(this,layout,library);
                listing.setName(library.getName());
                listing.setSubject(library.getSubject());
                listing.setCardCount(library.getCards().size() + " cards");
                layout.addView(listing.getPanel());
                listings.add(listing);

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
        findViewById(R.id.mylibraries_back_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(instance, MainMenu.class));
                overridePendingTransition(0,0);
            }
        });
        //Handle when the add library button is clicked
        findViewById(R.id.mylibraries_add_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(instance, ManageLibrary.class));
                overridePendingTransition(0,0);
            }
        });
        for (LibraryListing item : listings) {
            final LibraryListing listing = item;

            //Handle when each listing is clicked
            listing.getPanel().findViewById(R.id.libraryListing_bodyConstraint).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("cDebug","Testttt");
                }
            });
            //Handle when each listings edit button is clicked
            listing.getPanel().findViewById(R.id.mylibraries_listing_edit_button).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(instance, ManageLibrary.class);

                    //Attach the listings library id
                    intent.putExtra("libraryId",String.valueOf(listing.getLibrary().getID()));
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
            });
            //Handle when each listings delete button is clicked
            listing.getPanel().findViewById(R.id.mylibraries_listing_delete_button).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog alertDialog = new AlertDialog.Builder(instance).create();
                    alertDialog.setTitle("Are you sure?");
                    alertDialog.setMessage("Are you sure you want to delete the '" + listing.getName() + "' library?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mainInstance.getLibraries().remove(listing.getLibrary().getID());
                            startActivity(new Intent(instance, MyLibraries.class));
                            overridePendingTransition(0,0);
                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                }
            });
        }
    }
}
