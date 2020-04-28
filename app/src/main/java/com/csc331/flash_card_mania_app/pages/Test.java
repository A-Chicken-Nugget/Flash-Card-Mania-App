package com.csc331.flash_card_mania_app.pages;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.csc331.flash_card_mania_app.Card;
import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.Main;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.TestQuestion;
import com.csc331.flash_card_mania_app.TestResult;
import com.csc331.flash_card_mania_app.components.CardDisplay;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

public class Test extends AppCompatActivity {
    private Main mainInstance = Main.getInstance();
    private Library library;
    private ArrayList<Card> cardPool = new ArrayList<>();
    private HashMap<Integer,String> answers = new HashMap<>();
    private CardDisplay cardDisplay;
    private long timeStarted;
    private int currentQuestion = 1;

    public void completeTest() {
        ArrayList<TestQuestion> testQuestions = new ArrayList<>();
        int numberCorrect = 0;

        for (int q = 1; q < cardPool.size()+1; q++) {
            Card card = cardPool.get(q-1);
            boolean correct = false;

            if (answers.get(q) != null && card.getBack().getText().toLowerCase().equals(answers.get(q).toLowerCase())) {
                correct = true;
                numberCorrect++;
            }
            if (!card.getFront().getType()) {
                testQuestions.add(new TestQuestion(card.getFront().getText(),answers.get(q),card.getBack().getText(),correct));
            } else {
                testQuestions.add(new TestQuestion(card.getFront().getImageInfo(),answers.get(q),card.getBack().getText(),correct));
            }
        }
        mainInstance.getProfile().addTestScore(new TestResult(library.getName() + " Test",(int)(100*(double)numberCorrect/cardPool.size()),(int)((System.currentTimeMillis()/1000L)-timeStarted),testQuestions));
        startActivity(new Intent(this, TestResults.class));
        overridePendingTransition(0,0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Test instance = this;

        library = mainInstance.getLibraryById(UUID.fromString(getIntent().getStringExtra("libraryId")));

        final int cardCount = getIntent().getIntExtra("cardCount",0);
        final String difficulty = getIntent().getStringExtra("difficulty");
        final Boolean showHints = getIntent().getBooleanExtra("showHints",false);
        final int timerLength = 20;//getIntent().getIntExtra("timerLength",1)*60;

        //Set this pages view layout
        setContentView(R.layout.test);
        //Remove the default app header
        getSupportActionBar().hide();
        //Remove the device notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ((TextView)findViewById(R.id.test_libraryName)).setText(library.getName());
        ((TextView)findViewById(R.id.test_questionDisplay)).setText("Question 1 out of " + cardCount);

        //
        // Card pool
        //
        if (difficulty.equals("All difficulties")) {
            cardPool = library.getCards();
            Collections.shuffle(cardPool);
        } else {
            ArrayList<Card> cards = library.getCardsFromDifficulty(Card.Difficulty.difficultyFromName(difficulty));

            Collections.shuffle(cards);
            for (int i = 0; i < cardCount; i++) {
                cardPool.add(cards.get(i));
            }
        }

        if (showHints) {
            ((TextInputEditText) findViewById(R.id.test_questionInput)).setHint(cardPool.get(0).getHint());
        }

        //
        // Test start
        //
        new CountDownTimer(6000, 1000) {
            public void onTick(long millisUntilFinished) {
                long timeLeft = (millisUntilFinished/1000);

                if (timeLeft < 5) {
                    ((TextView)findViewById(R.id.test_countDownText)).setText(((millisUntilFinished/1000) + 1) + "");
                }
            }
            public void onFinish() {
                findViewById(R.id.test_beginPanel).setVisibility(View.GONE);
                timeStarted = System.currentTimeMillis()/1000L;

                //Handle timer display
                new CountDownTimer(timerLength*1000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        long timeLeft = (millisUntilFinished/1000);

                        ((ProgressBar)findViewById(R.id.test_countDownBar)).setProgress((int)(100-(100*((double)(timerLength-timeLeft)/timerLength))));
                        if (timeLeft == 60) {
                            Toast.makeText(instance, "1 minute left", Toast.LENGTH_SHORT).show();
                        } else if (timeLeft == 10) {
                            Toast.makeText(instance, "10 seconds left", Toast.LENGTH_SHORT).show();
                        } else if (timeLeft <= 0) {
                            findViewById(R.id.test_timeUpPanel).setVisibility(View.VISIBLE);
                        }
                    }
                    public void onFinish() {
                        findViewById(R.id.test_beginPanel).setVisibility(View.GONE);
                    }
                }.start();
            }
        }.start();

        cardDisplay = new CardDisplay(this,(ViewGroup)findViewById(R.id.test_cardDisplay),cardPool.get(0),false);

        //
        // ETC
        //

        //Handle when the view results button is clicked
        findViewById(R.id.test_timeUpButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                completeTest();
            }
        });

        //Handle when the next question button is clicked
        findViewById(R.id.test_submitButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String answer = ((TextInputEditText)findViewById(R.id.test_questionInput)).getText().toString();

                if (answer.equals("")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(instance).create();
                    alertDialog.setTitle("Are you sure?");
                    alertDialog.setMessage("Are you sure you want to continue without answering this question?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (currentQuestion < cardCount) {
                                answers.put(currentQuestion,answer);
                                currentQuestion++;
                                ((TextView)findViewById(R.id.test_questionDisplay)).setText("Question " + currentQuestion + " out of " + cardCount);
                                ((TextInputEditText)findViewById(R.id.test_questionInput)).setText("");
                                cardDisplay = new CardDisplay(instance,(ViewGroup)findViewById(R.id.test_cardDisplay),cardPool.get(currentQuestion-1),false);
                                if (showHints) {
                                    ((TextInputEditText)findViewById(R.id.test_questionInput)).setHint(cardPool.get(currentQuestion-1).getHint());
                                }
                                if (currentQuestion == cardCount) {
                                    ((Button)findViewById(R.id.test_submitButton)).setText("Finish test");
                                }
                            } else {
                                completeTest();
                            }
                            cardDisplay.updateContents();
                        }
                    });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    alertDialog.show();
                } else {
                    if (currentQuestion < cardCount) {
                        answers.put(currentQuestion,answer);
                        currentQuestion++;
                        ((TextView)findViewById(R.id.test_questionDisplay)).setText("Question " + currentQuestion + " out of " + cardCount);
                        ((TextInputEditText)findViewById(R.id.test_questionInput)).setText("");
                        cardDisplay = new CardDisplay(instance,(ViewGroup)findViewById(R.id.test_cardDisplay),cardPool.get(currentQuestion-1),false);
                        if (showHints) {
                            ((TextInputEditText)findViewById(R.id.test_questionInput)).setHint(cardPool.get(currentQuestion-1).getHint());
                        }
                        if (currentQuestion == cardCount) {
                            ((Button)findViewById(R.id.test_submitButton)).setText("Finish test");
                        }
                    } else {
                        completeTest();
                    }
                    cardDisplay.updateContents();
                }
            }
        });

        //Handle when the back button is clicked
        findViewById(R.id.test_backButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(instance).create();
                alertDialog.setTitle("Quit test?");
                alertDialog.setMessage("Are you sure you want to quit this test? Your progress will be lost!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(instance, MainMenu.class));
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
}
