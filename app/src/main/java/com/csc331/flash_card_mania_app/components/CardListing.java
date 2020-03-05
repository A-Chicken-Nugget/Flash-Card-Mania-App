package com.csc331.flash_card_mania_app.components;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csc331.flash_card_mania_app.Card;
import com.csc331.flash_card_mania_app.CardSide;
import com.csc331.flash_card_mania_app.Library;
import com.csc331.flash_card_mania_app.R;

import java.io.BufferedInputStream;
import java.io.File;

public class CardListing {
    private View panel;
    private Card card;

    public CardListing(Context context, LinearLayout layout, Card card) {
        panel = LayoutInflater.from(context).inflate(R.layout.card_listing, layout, false);
        this.card = card;

        CardSide cardFront = card.getFront();

        if (cardFront.getType()) {
            View imageView = panel.findViewById(R.id.cardListing_image);

            ((ImageView)imageView).setImageBitmap(BitmapFactory.decodeStream(new BufferedInputStream(cardFront.getImageStream())));
            panel.findViewById(R.id.cardListing_text).setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
        } else {
            ((TextView)panel.findViewById(R.id.cardListing_text)).setText(cardFront.getText());
        }
    }

    public View getPanel() {
        return panel;
    }
    public Card getCard() {
        return card;
    }
}
