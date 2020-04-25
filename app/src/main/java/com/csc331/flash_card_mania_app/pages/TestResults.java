package com.csc331.flash_card_mania_app.pages;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.csc331.flash_card_mania_app.Card;
import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.TestQuestion;
import com.csc331.flash_card_mania_app.TestResult;
import com.csc331.flash_card_mania_app.components.ButtonField;
import com.csc331.flash_card_mania_app.components.CheckboxField;
import com.csc331.flash_card_mania_app.components.DropdownField;
import com.csc331.flash_card_mania_app.components.QuestionListing;

import java.util.ArrayList;
import java.util.UUID;

public class TestResults extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();
    private TestResult test = mainInstance.getProfile().getLatestTestResult();

    public static String secondsToTime(int seconds)
    {
        int hours = (int) seconds / 3600;
        int remainder = (int) seconds - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        return mins + " minutes " + secs + " seconds";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final TestResults instance = this;

        //Set this pages view layout
        setContentView(R.layout.test_results);
        //Remove the default app header
        getSupportActionBar().hide();
        //Remove the device notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ((TextView)findViewById(R.id.testResults_score)).setText("Score - " + test.getPercentCorrect() + "% - " + test.getLetterGrade());
        ((TextView)findViewById(R.id.testResults_answerResults)).setText("You answered " + test.getQuestionsCorrectlyAnswered() + " correctly out of " + test.getTotalQuestions());
        ((TextView)findViewById(R.id.testResults_timeSpent)).setText("You took " + secondsToTime(test.getTimeSpent()));

        LinearLayout layout = findViewById(R.id.testResults_questionsLayout);

        for (TestQuestion question : test.getTestQuestions()) {
            layout.addView(new QuestionListing(this,layout,question).getPanel());

            Space space = new Space(this);
            space.setMinimumWidth(layout.getWidth());
            space.setMinimumHeight(20);
            layout.addView(space);
        }

        //Handle when the back button is clicked
        findViewById(R.id.testResults_backButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(instance, MainMenu.class));
                overridePendingTransition(0,0);
            }
        });
    }
}