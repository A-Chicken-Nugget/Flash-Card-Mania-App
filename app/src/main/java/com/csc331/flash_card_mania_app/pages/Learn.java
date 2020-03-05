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
    private Card card;
    private CardDisplay cardDisplay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Learn instance = this;
        //Get the library id
        final String id = getIntent().getStringExtra("libraryId");
        final String randomized = getIntent().getStringExtra("isRandom");
        //If the id isn't null, get the library from its passed in id
        final Library library = (id != null ? mainInstance.getLibraryById(UUID.fromString(id)) : null);

        //Set this pages view layout
        setContentView(R.layout.learn);
        ((TextView)findViewById(R.id.learn_library_name)).setText(library.getName());
        //Remove the default app header
        getSupportActionBar().hide();
        //Remove the device notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ArrayList<Card> cards = library.getCards();
        if (randomized == "True") {
            Collections.shuffle(cards);
        }
        card = cards.get(0);
        cardDisplay = new CardDisplay(this,(ViewGroup)findViewById(R.id.learn_cardDisplay),card);

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

        //Handle when the back button is clicked
        findViewById(R.id.learn_back_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instance, SelectLibrary.class);

                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        //if (cards.size() > 0) {
        //} else {
        //}
    }
}
