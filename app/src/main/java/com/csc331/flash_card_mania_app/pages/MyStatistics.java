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
import com.csc331.flash_card_mania_app.components.StatisticsListing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


public class MyStatistics extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();

    public static String secondsToTime(int seconds)
    {
        long millis = seconds*1000L;
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        return hms;
    }

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

        ArrayList<StatisticsListing> learningListings = new ArrayList<>();
        ArrayList<StatisticsListing> testingListings = new ArrayList<>();

        LinearLayout testingHistorylayout = findViewById(R.id.myStatistics_testingHistoryLayout);
        LinearLayout learningHistorylayout = findViewById(R.id.myStatistics_learningHistoryLayout);

        ArrayList<TestResult> testResults = mainInstance.getProfile().getTestResults();
        HashMap<UUID,Library> libraries = mainInstance.getLibraries();

        //Create classes to handle creating the listing views for both testing/learning history
        for (Map.Entry<UUID,Library> entry : libraries.entrySet()) {
            Library library = entry.getValue();
            Integer time = mainInstance.getProfile().getTimeSpentLearning().get(library.getID());
            if (time == null) {continue;}
            final StatisticsListing listing = new StatisticsListing(this, learningHistorylayout, library);
            listing.setName(library.getName());
            listing.setTime("Time spent: " + secondsToTime(time));
            listing.setDate("");
            learningHistorylayout.addView(listing.getPanel());
            learningListings.add(listing);
            Space space = new Space(this);
            space.setMinimumWidth(learningHistorylayout.getWidth());
            space.setMinimumHeight(20);
            learningHistorylayout.addView(space);
        }

        for (TestResult result : testResults) {
            final StatisticsListing listing = new StatisticsListing(this, testingHistorylayout);
            listing.setName(result.getTestName());
            listing.setTime("Time spent: " + secondsToTime(result.getTimeSpent()));
            listing.setDate("Date: " + result.getDate());
            testingHistorylayout.addView(listing.getPanel());
            testingListings.add(listing);
            Space space = new Space(this);
            space.setMinimumWidth(testingHistorylayout.getWidth());
            space.setMinimumHeight(20);
            testingHistorylayout.addView(space);
        }

        findViewById(R.id.myStatistics_testingHistory).setVisibility(View.VISIBLE);
        findViewById(R.id.myStatistics_learningPageButton).setHovered(false);
        findViewById(R.id.myStatistics_testingPageButton).setHovered(true);

        findViewById(R.id.myStatistics_learningPageButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(R.id.myStatistics_learningHistory).setVisibility(View.VISIBLE);
                findViewById(R.id.myStatistics_testingPageButton).setHovered(false);
                findViewById(R.id.myStatistics_learningPageButton).setHovered(true);
                findViewById(R.id.myStatistics_testingHistory).setVisibility(View.GONE);
            }
        });

        findViewById(R.id.myStatistics_testingPageButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                findViewById(R.id.myStatistics_testingHistory).setVisibility(View.VISIBLE);
                findViewById(R.id.myStatistics_testingPageButton).setHovered(true);
                findViewById(R.id.myStatistics_learningPageButton).setHovered(false);
                findViewById(R.id.myStatistics_learningHistory).setVisibility(View.GONE);
            }
        });

        //Handle when the back button is clicked
        findViewById(R.id.myStatistics_backButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(instance, MainMenu.class));
                overridePendingTransition(0,0);
            }
        });
    }
}
