package com.csc331.flash_card_mania_app.pages;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Collections;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.csc331.flash_card_mania_app.Card;
import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.components.CardDisplay;
import com.csc331.flash_card_mania_app.components.CardListing;
import com.csc331.flash_card_mania_app.components.LibraryListing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Learn extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();
    private Library library;
    private ArrayList<Card> cardPool = new ArrayList<>();
    private CardDisplay cardDisplay;
    private long timeStarted;
    private int currentCard = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Learn instance = this;

        final Library library = mainInstance.getLibraryById(UUID.fromString(getIntent().getStringExtra("libraryId")));
        final Boolean randomizeOrder = getIntent().getBooleanExtra("randomizeOrder",false);

        //Set this pages view layout
        setContentView(R.layout.learn);
        //Remove the default app header
        getSupportActionBar().hide();
        //Remove the device notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ((TextView)findViewById(R.id.learn_library_name)).setText(library.getName());

        ArrayList<Card> cardPool = library.getCards();
        if (randomizeOrder) {
            Collections.shuffle(cardPool);
        }
        final ArrayList<Card> cards = new ArrayList<Card>(cardPool);
        cardDisplay = new CardDisplay(this,(ViewGroup)findViewById(R.id.learn_cardDisplay),cardPool.get(currentCard),true);

        //Handle when the card display is clicked
        findViewById(R.id.learn_cardDisplay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(v, "scaleX", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(v, "scaleX", 0f, 1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        cardDisplay.flip();
                        oa2.start();
                    }
                });
                oa1.start();
            }
        });

        //Handle when the next button is clicked
        findViewById(R.id.learn_next_card_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentCard++;

                if (currentCard > cards.size()) {
                    currentCard = 0;
                }
                cardDisplay = new CardDisplay(instance,(ViewGroup)findViewById(R.id.learn_cardDisplay),cards.get(currentCard),true);
            }
        });

        //Handle when the next button is clicked
        findViewById(R.id.learn_next_card_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentCard++;

                if (currentCard > cards.size()) {
                    currentCard = 0;
                }
                findViewById(R.id.learn_hintDisplay).setVisibility(View.GONE);
                cardDisplay = new CardDisplay(instance,(ViewGroup)findViewById(R.id.learn_cardDisplay),cards.get(currentCard),true);
            }
        });

        //Handle when the show hint button is clicked
        findViewById(R.id.learn_showHintButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((TextView)findViewById(R.id.learn_hintDisplay)).setText(cards.get(currentCard).getHint());
                findViewById(R.id.learn_hintDisplay).setVisibility(View.VISIBLE);
            }
        });

        //Handle when the back button is clicked
        findViewById(R.id.learn_back_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instance, MainMenu.class);

                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }
}
