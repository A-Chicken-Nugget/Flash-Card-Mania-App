package com.csc331.flash_card_mania_app.pages;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.components.LibraryListing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyLibraries extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final MyLibraries instance = this;

        setContentView(R.layout.my_libraries);

        Log.d("cDebug","Count: " + mainInstance.getLibraries().size());

        HashMap<String,Library> libraries = mainInstance.getLibraries();
        LinearLayout layout = findViewById(R.id.library_layout);
        layout.setDividerPadding(25);

        for (Map.Entry<String,Library> library : libraries.entrySet()) {
            LibraryListing library_listing = new LibraryListing(this,layout);
            library_listing.setName(library.getValue().getName());
            library_listing.setCardCount(library.getValue().getCards().size() + " cards");
        }
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instance, MainMenu.class);

                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }
}
