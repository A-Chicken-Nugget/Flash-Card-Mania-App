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
import com.csc331.flash_card_mania_app.components.ButtonField;
import com.csc331.flash_card_mania_app.components.CheckboxField;
import com.csc331.flash_card_mania_app.components.DropdownField;
import com.csc331.flash_card_mania_app.components.LibraryListing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SelectSettings extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final SelectSettings instance = this;

        final Library library = mainInstance.getLibraryById(UUID.fromString(getIntent().getStringExtra("libraryId")));
        final Boolean testType = getIntent().getBooleanExtra("testType",false);

        //Set this pages view layout
        setContentView(R.layout.select_settings);
        //Remove the default app header
        getSupportActionBar().hide();
        //Remove the device notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LinearLayout layout = findViewById(R.id.selectSettings_settingsLayout);

        if (testType) {
            ((TextView)findViewById(R.id.selectSettings_title)).setText("Test Settings");
            ((TextView)findViewById(R.id.selectSettings_title)).setGravity(Gravity.CENTER);

            //Number of cards
            final DropdownField cardAmount = new DropdownField(this,layout,"Number of cards:", new ArrayList<String>() {{
                add("--- SELECT ---");
            }});
            layout.addView(cardAmount.getPanel());

            //Difficulty
            final DropdownField difficulty = new DropdownField(this,layout,"Card difficulty:", new ArrayList<String> (Card.Difficulty.listAll(true)) {{
                add("All difficulties");
            }});
            layout.addView(difficulty.getPanel());

            //Include hints
            final CheckboxField includeHints = new CheckboxField(this,layout,"Include hints:");
            layout.addView(includeHints.getPanel());

            //Timer length
            final DropdownField timerLength = new DropdownField(this,layout,"Timer length:",new ArrayList<String>() {{
                add("--- SELECT ---");
                add("1 minute");
                add("2 minutes");
                add("3 minutes");
                add("4 minutes");
                add("5 minutes");
                add("10 minutes");
                add("15 minutes");
            }});
            layout.addView(timerLength.getPanel());

            //Start test
            ButtonField startTest = new ButtonField(this,layout,"Start test");
            layout.addView(startTest.getPanel());

            startTest.getPanel().findViewById(R.id.field_button).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (cardAmount.getSelectedOption().equals("--- SELECT ---")) {
                        Toast.makeText(instance, "Please select a number of cards!", Toast.LENGTH_SHORT).show();
                    } else if (Integer.parseInt(cardAmount.getSelectedOption().split(" ")[0]) < 5) {
                        Toast.makeText(instance, "The number of cards must be greater than 5!", Toast.LENGTH_SHORT).show();
                    } else if (difficulty.getSelectedOption().equals("--- SELECT ---")) {
                        Toast.makeText(instance, "Please select a difficulty!", Toast.LENGTH_SHORT).show();
                    } else if (timerLength.getSelectedOption().equals("--- SELECT ---")) {
                        Toast.makeText(instance, "Please select a timer length!", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(instance, Test.class);

                        intent.putExtra("libraryId",String.valueOf(library.getID()));
                        intent.putExtra("cardCount",Integer.parseInt(cardAmount.getSelectedOption().split(" ")[0]));
                        intent.putExtra("difficulty",difficulty.getSelectedOption());
                        intent.putExtra("showHints",includeHints.getCheckedValue());
                        intent.putExtra("timerLength",Integer.parseInt(timerLength.getSelectedOption().split(" ")[0]));
                        startActivity(intent);
                        overridePendingTransition(0,0);
                    }
                }
            });

            //When difficulty dropdown is changed
            ((Spinner)difficulty.getPanel().findViewById(R.id.field_dropdown)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String selection = difficulty.getSelectedOption();

                    if (!selection.equals("--- SELECT ---")) {
                        ArrayList<Card> cardPool;
                        ArrayList<String> cards = new ArrayList<>();

                        if (selection.equals("All difficulties")) {
                            cardPool = library.getCards();
                        } else {
                            cardPool = library.getCardsFromDifficulty(Card.Difficulty.difficultyFromName(selection));
                        }

                        int optionsToSet = (int) Math.ceil((double)cardPool.size()/5);

                        cards.add("--- SELECT ---");
                        if (optionsToSet > 1) {
                            for (int i = 0; i < optionsToSet-1; i++) {
                                cards.add((i + 1) * 5 + " cards");
                            }
                        }
                        cards.add(cardPool.size() + " cards");

                        cardAmount.setOptions(cards);
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            });
        } else {
            ((TextView)findViewById(R.id.selectSettings_title)).setText("Learn Settings");
            ((TextView)findViewById(R.id.selectSettings_title)).setGravity(Gravity.CENTER);

            //Randomize order
            final CheckboxField randomizeOrder = new CheckboxField(this,layout,"Randomize order:");
            layout.addView(randomizeOrder.getPanel());

            //Start test
            ButtonField startLearning = new ButtonField(this,layout,"Start learning");
            layout.addView(startLearning.getPanel());

            startLearning.getPanel().findViewById(R.id.field_button).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(instance, Learn.class);

                    intent.putExtra("libraryId",String.valueOf(library.getID()));
                    intent.putExtra("randomizeOrder",randomizeOrder.getCheckedValue());
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
            });
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