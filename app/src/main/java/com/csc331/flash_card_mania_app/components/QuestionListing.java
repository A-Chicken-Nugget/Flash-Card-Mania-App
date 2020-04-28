package com.csc331.flash_card_mania_app.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csc331.flash_card_mania_app.Card;
import com.csc331.flash_card_mania_app.CardSide;
import com.csc331.flash_card_mania_app.R;
import com.csc331.flash_card_mania_app.TestQuestion;

public class QuestionListing {
    private View panel;
    private TestQuestion question;

    public QuestionListing(Context context, LinearLayout layout, TestQuestion question) {
        panel = LayoutInflater.from(context).inflate(R.layout.question_listing, layout, false);
        this.question = question;

        if (question.getQuestionType()) {
            ((TextView)panel.findViewById(R.id.question_questionText)).setText("Q.) " + question.getQuestion());
        } else {
            panel.findViewById(R.id.question_questionText).setVisibility(View.GONE);
            panel.findViewById(R.id.question_questionImage).setVisibility(View.VISIBLE);
        }
        ((TextView)panel.findViewById(R.id.question_correctAnswer)).setText("Correct: " + question.getCorrectAnswer());
        ((TextView)panel.findViewById(R.id.question_inputtedAnswer)).setText("You said: " + (question.getAnswer() != null ? question.getAnswer() : ""));
        if (!question.didGetCorrect()) {
            panel.findViewById(R.id.question_correctImage).setVisibility(View.GONE);
            panel.findViewById(R.id.question_incorrectImage).setVisibility(View.VISIBLE);
        }
    }

    public View getPanel() {
        return panel;
    }
    public TestQuestion getQuestion() {
        return question;
    }
}
