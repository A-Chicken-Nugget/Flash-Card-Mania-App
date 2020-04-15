package com.csc331.flash_card_mania_app.pages;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.csc331.flash_card_mania_app.Card;
import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.components.CardListing;
import com.csc331.flash_card_mania_app.components.LibraryListing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ViewLibrary extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ViewLibrary instance = this;
        //Get the library id
        final String id = getIntent().getStringExtra("libraryId");
        //If the id isn't null, get the library from its passed in id
        final Library library = (id != null ? mainInstance.getLibraryById(UUID.fromString(id)) : null);

        //Set this pages view layout
        setContentView(R.layout.view_library);
        //Remove the default app header
        getSupportActionBar().hide();
        //Remove the device notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Set the description field to the description of the library
        ((TextView)findViewById(R.id.viewLibrary_description)).setText(library.getDescription());

        //Handle when the back button is clicked
        findViewById(R.id.viewLibrary_back_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instance, MyLibraries.class);

                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //Handle when the add card button is clicked
        findViewById(R.id.viewLibrary_add_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instance, ManageCard.class);
                intent.putExtra("libraryId",id);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //Handle when the edit description button is clicked
        findViewById(R.id.viewLibrary_editButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(R.id.viewLibrary_description).setVisibility(View.GONE);
                findViewById(R.id.viewLibrary_editButton).setVisibility(View.GONE);

                ((TextView)findViewById(R.id.viewLibrary_descriptionInputValue)).setText(library.getDescription());
                findViewById(R.id.viewLibrary_descriptionInput).setVisibility(View.VISIBLE);
                findViewById(R.id.viewLibrary_saveDescription).setVisibility(View.VISIBLE);
                findViewById(R.id.viewLibrary_discardDescriptionChanges).setVisibility(View.VISIBLE);
            }
        });
        //Handle when the save description button is clicked
        findViewById(R.id.viewLibrary_saveDescription).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(R.id.viewLibrary_descriptionInput).setVisibility(View.GONE);
                findViewById(R.id.viewLibrary_saveDescription).setVisibility(View.GONE);
                findViewById(R.id.viewLibrary_discardDescriptionChanges).setVisibility(View.GONE);

                library.setDescription(((TextView)findViewById(R.id.viewLibrary_descriptionInputValue)).getText().toString());
                ((TextView)findViewById(R.id.viewLibrary_description)).setText(library.getDescription());
                findViewById(R.id.viewLibrary_description).setVisibility(View.VISIBLE);
                findViewById(R.id.viewLibrary_editButton).setVisibility(View.VISIBLE);

                Toast.makeText(instance, "Successfully updated library description", Toast.LENGTH_SHORT).show();
                //Close keyboard
                InputMethodManager imm = (InputMethodManager) instance.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        //Handle when the discard description changes button is clicked
        findViewById(R.id.viewLibrary_discardDescriptionChanges).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(R.id.viewLibrary_descriptionInput).setVisibility(View.GONE);
                findViewById(R.id.viewLibrary_saveDescription).setVisibility(View.GONE);
                findViewById(R.id.viewLibrary_discardDescriptionChanges).setVisibility(View.GONE);

                findViewById(R.id.viewLibrary_description).setVisibility(View.VISIBLE);
                findViewById(R.id.viewLibrary_editButton).setVisibility(View.VISIBLE);

                //Close keyboard
                InputMethodManager imm = (InputMethodManager) instance.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        ArrayList<Card> cards = library.getCards();
        //Keep track of the card listing instances
        ArrayList<CardListing> listings = new ArrayList<>();
        LinearLayout layout = findViewById(R.id.viewLibrary_cardLayout);

        if (cards.size() > 0) {
            for (Card card : cards) {
                final CardListing listing = new CardListing(this,layout,card);
                layout.addView(listing.getPanel());
                listings.add(listing);

                Space space = new Space(this);
                space.setMinimumWidth(layout.getWidth());
                space.setMinimumHeight(20);
                layout.addView(space);
              
                //Handle when the edit button is clicked
                listing.getPanel().findViewById(R.id.cardListing_editButton).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(instance, ManageCard.class);
                        intent.putExtra("libraryId",id);
                        intent.putExtra("cardId",listing.getCard().getID().toString());
                        startActivity(intent);
                        overridePendingTransition(0,0);
                    }
                });
                //Handle when each listings delete button is clicked
                listing.getPanel().findViewById(R.id.cardListing_deleteButton).setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        AlertDialog alertDialog = new AlertDialog.Builder(instance).create();
                        alertDialog.setTitle("Are you sure?");
                        alertDialog.setMessage("Are you sure you want to delete the card?");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                library.deleteCard(listing.getCard());

                                Intent intent = new Intent(instance, ViewLibrary.class);
                                intent.putExtra("libraryId",library.getID().toString());
                                startActivity(intent);
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
        } else {
            TextView text = new TextView(this);
            text.setText("No cards have been created. Click the plus symbol to create one");
            text.setTextSize(18);
            text.setTextColor(Color.BLACK);
            text.setGravity(Gravity.CENTER);
            layout.addView(text);
        }
    }
}
