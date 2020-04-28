package com.csc331.flash_card_mania_app.pages;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.TestResult;
import com.csc331.flash_card_mania_app.components.LibraryListing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MyStatistics extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final MyStatistics instance = this;

        //Set this pages view layout
        setContentView(R.layout.my_statistics);
        //Remove the default app header
        getSupportActionBar().hide();
        //Remove the device notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LinearLayout testingHistorylayout = findViewById(R.id.myStatistics_testingHistoryLayout);
        LinearLayout learingHistorylayout = findViewById(R.id.myStatistics_learningHistoryLayout);

        //Create classes to handle creating the listing views for both testing/learning history
        for (TestResult question : mainInstance.getProfile().getTestResults()) {

        }

        //Handle when the back button is clicked
        findViewById(R.id.myStatistics_backButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(instance, MainMenu.class));
                overridePendingTransition(0,0);
            }
        });
    }
}
