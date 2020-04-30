package com.csc331.flash_card_mania_app.pages;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import com.csc331.flash_card_mania_app.Card;
import com.csc331.flash_card_mania_app.ImageInfo;
import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.components.CardDisplay;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ManageCard extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();
    private static final int PICK_PHOTO = 45;
    private Library library;
    private Card card;
    private CardDisplay cardDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final ManageCard instance = this;
        //If they are editing a card, get the given id
        final String libraryId = getIntent().getStringExtra("libraryId");
        final String cardId = getIntent().getStringExtra("cardId");
        library = mainInstance.getLibraryById(UUID.fromString(libraryId));
        //Create a new card for this form
        card = (cardId == null ? new Card() : library.getCardFromID(UUID.fromString(cardId)));

        //Set this pages view layout
        setContentView(R.layout.manage_card);
        //Remove the default app header
        getSupportActionBar().hide();
        //Remove the device notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Add options to difficulty dropdown
        final Spinner difficultyDropdown = findViewById(R.id.manageCard_difficultyDropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Card.Difficulty.listAll(true));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyDropdown.setAdapter(adapter);

        //Set the page title/submit button text if you are updating a card
        if (cardId != null) {
            ((TextView)findViewById(R.id.manageCard_pageTitle)).setText("Update Card");
            ((Button)findViewById(R.id.manageCard_submitButton)).setText("Update");

            if (card.getFront().getType()) {
                ImageInfo imageInfo = card.getFront().getImageInfo();

                ((CheckBox)findViewById(R.id.manageCard_sideTypeCheckbox)).setChecked(true);
                findViewById(R.id.manageCard_textInputLayout).setVisibility(View.GONE);
                findViewById(R.id.manageCard_imageInputLayout).setVisibility(View.VISIBLE);
                if (imageInfo != null) {
                    ((TextView)findViewById(R.id.manageCard_imageName)).setText(imageInfo.getName());
                }
            }
            difficultyDropdown.setSelection(adapter.getPosition(card.getDifficulty().getName()));
        } else {
            difficultyDropdown.setSelection(0);
        }
        cardDisplay = new CardDisplay(this,(ViewGroup)findViewById(R.id.manageCard_cardDisplay),card,true);

        //Populate the form fields with the cards general data
        if (!card.getFront().getType()) {
            ((TextView)findViewById(R.id.manageCard_inputText)).setText(card.getFront().getText());
        }
        ((TextView)findViewById(R.id.manageCard_hintInputField)).setText(card.getHint());

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
                            }
                        } else {
                            findViewById(R.id.manageCard_sideTypeCheckbox).setVisibility(View.GONE);
                            findViewById(R.id.manageCard_imageInputLayout).setVisibility(View.GONE);
                            findViewById(R.id.manageCard_textInputLayout).setVisibility(View.VISIBLE);
                        }
                        ((TextView)findViewById(R.id.manageCard_inputText)).setText(card.getShownSide().getText());
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
                    ((TextView)findViewById(R.id.manageCard_inputText)).setText(card.getShownSide().getText());
                } else {
                    card.getFront().setType(true);
                    cardDisplay.getPanel().findViewById(R.id.card_textFront).setVisibility(View.GONE);
                    cardDisplay.getPanel().findViewById(R.id.card_imageFront).setVisibility(View.VISIBLE);

                    findViewById(R.id.manageCard_imageInputLayout).setVisibility(View.VISIBLE);
                    findViewById(R.id.manageCard_textInputLayout).setVisibility(View.GONE);
                }
            }
        });
        //Handle when the submit button is clicked
        findViewById(R.id.manageCard_submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!difficultyDropdown.getSelectedItem().toString().equalsIgnoreCase("--- SELECT ---")) {
                    //Set cards general data from form
                    card.setDifficulty(Card.Difficulty.difficultyFromName(difficultyDropdown.getSelectedItem().toString()));
                    card.setHint(((TextView)findViewById(R.id.manageCard_hintInputField)).getText().toString());

                    //If the card is new, then add it to the library
                    if (cardId == null) {
                        library.addCard(card);
                        Toast.makeText(instance, "New card added to library", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(instance, "Card successfully updated", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(instance, ViewLibrary.class);
                    intent.putExtra("libraryId",libraryId);
                    startActivity(intent);
                    overridePendingTransition(0,0);

                    mainInstance.saveData();
                } else {
                    Toast.makeText(instance, "Please select a difficulty option", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();

            try {
                InputStream stream = getContentResolver().openInputStream(selectedImage);
                byte[] buffer = new byte[stream.available()];
                stream.read(buffer);
                stream.close();

                DocumentFile file = DocumentFile.fromSingleUri(this, selectedImage);
                ImageInfo imageInfo = new ImageInfo(file.getName(),file.length(),file.getType(),buffer);

                card.getFront().setImageInfo(imageInfo);
                ((TextView)findViewById(R.id.manageCard_imageName)).setText(file.getName());
                cardDisplay.updateContents();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
