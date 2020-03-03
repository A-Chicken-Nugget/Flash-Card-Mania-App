package com.csc331.flash_card_mania_app.pages;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.csc331.flash_card_mania_app.Card;
import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.components.CardDisplay;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ManageCard extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();
    private static final int PICK_PHOTO = 45;
    private Card card;
    private CardDisplay cardDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ManageCard instance = this;
        //If they are editing a card, get the given id
        final String libraryId = getIntent().getStringExtra("libraryId");
        final String cardId = getIntent().getStringExtra("cardId");
        //Create a new card for this form
        card = new Card();

        //If a cardId is passed in, set the new cards info accordingly
        if (cardId != null) {
            Card cardRef = mainInstance.getLibraryById(UUID.fromString(libraryId)).getCardFromID(UUID.fromString(cardId));

            //Front content
            card.getFront().setType(cardRef.getFront().getType());
            if (cardRef.getFront().getType()) {
                card.getFront().setImage(cardRef.getFront().getContents());
            } else {
                card.getFront().setText(cardRef.getFront().getContents());
            }

            //Back content
            card.getBack().setText(cardRef.getBack().getContents());
        }


        //Set this pages view layout
        setContentView(R.layout.manage_card);
        //Remove the default app header
        getSupportActionBar().hide();
        //Remove the device notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        cardDisplay = new CardDisplay(this,(ViewGroup)findViewById(R.id.manageCard_cardDisplay),card);

        //Populate fields with card data
        if (!card.getShownSide().getType()) {
            ((TextView)findViewById(R.id.manageCard_inputText)).setText(cardDisplay.getCard().getShownSide().getContents());
        } else {

        }

        //Handle when the back button is clicked
        findViewById(R.id.manageCard_back_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(instance, ViewLibrary.class);
                intent.putExtra("libraryId",libraryId);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //Handle when the text input box is changed
        ((EditText)findViewById(R.id.manageCard_inputText)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (cardDisplay.getCard().getCurrentSide()) {
                    ((TextView)cardDisplay.getPanel().findViewById(R.id.card_textFront)).setText(s);
                } else {
                    ((TextView)cardDisplay.getPanel().findViewById(R.id.card_textBack)).setText(s);
                }
                card.getShownSide().setText(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        //Handle when the text box loses focus
        findViewById(R.id.manageCard_inputText).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //Close keyboard
                    InputMethodManager imm = (InputMethodManager) instance.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        //Handle when the browse for image button is clicked
        findViewById(R.id.manageCard_browseForImage).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Card Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_PHOTO);
            }
        });
        //Handle when the card display is clicked
        findViewById(R.id.manageCard_cardDisplay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = v;
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

                        if (card.getCurrentSide()) {
                            findViewById(R.id.manageCard_sideTypeCheckbox).setVisibility(View.VISIBLE);
                            if (card.getFront().getType()) {
                                findViewById(R.id.manageCard_textInputLayout).setVisibility(View.GONE);
                                findViewById(R.id.manageCard_imageInputLayout).setVisibility(View.VISIBLE);
                            } else {
                                findViewById(R.id.manageCard_imageInputLayout).setVisibility(View.GONE);
                                findViewById(R.id.manageCard_textInputLayout).setVisibility(View.VISIBLE);
                                ((TextView)findViewById(R.id.manageCard_inputText)).setText(card.getShownSide().getContents());
                            }
                        } else {
                            findViewById(R.id.manageCard_sideTypeCheckbox).setVisibility(View.GONE);
                            findViewById(R.id.manageCard_imageInputLayout).setVisibility(View.GONE);
                            findViewById(R.id.manageCard_textInputLayout).setVisibility(View.VISIBLE);
                            ((TextView)findViewById(R.id.manageCard_inputText)).setText(card.getShownSide().getContents());
                        }
                    }
                });
                oa1.start();

                //Close keyboard
                InputMethodManager imm = (InputMethodManager) instance.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });
        //Handle when the card side type checkbox is clicked
        findViewById(R.id.manageCard_sideTypeCheckbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((CheckBox)v).isChecked()) {
                    card.getFront().setType(false);
                    cardDisplay.getPanel().findViewById(R.id.card_textFront).setVisibility(View.VISIBLE);
                    cardDisplay.getPanel().findViewById(R.id.card_imageFront).setVisibility(View.GONE);

                    findViewById(R.id.manageCard_imageInputLayout).setVisibility(View.GONE);
                    findViewById(R.id.manageCard_textInputLayout).setVisibility(View.VISIBLE);
                } else {
                    card.getFront().setType(true);
                    cardDisplay.getPanel().findViewById(R.id.card_textFront).setVisibility(View.GONE);
                    cardDisplay.getPanel().findViewById(R.id.card_imageFront).setVisibility(View.VISIBLE);

                    findViewById(R.id.manageCard_imageInputLayout).setVisibility(View.VISIBLE);
                    findViewById(R.id.manageCard_textInputLayout).setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();

            card.getFront().setImage(selectedImage.getPath());
            ((TextView)findViewById(R.id.manageCard_imageName)).setText("test");
            cardDisplay.updateContents();
        }
    }
}
