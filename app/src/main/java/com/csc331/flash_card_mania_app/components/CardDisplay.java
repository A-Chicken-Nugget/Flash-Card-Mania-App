package com.csc331.flash_card_mania_app.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csc331.flash_card_mania_app.Card;
import com.csc331.flash_card_mania_app.CardSide;
import com.csc331.flash_card_mania_app.R;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class CardDisplay {
    private Context context;
    private View panel;
    private Card card;

    public CardDisplay(Context context, ViewGroup layout, Card card) {
        this.context = context;
        panel = LayoutInflater.from(context).inflate(R.layout.card, layout, true);
        this.card = card;

        updateContents();
    }

    public void updateContents() {
        if (card.getCurrentSide()) {
            CardSide cardFront = card.getFront();

            ((TextView)panel.findViewById(R.id.card_sideDisplay)).setText("Front");
            if (cardFront.getType()) {
                View imageView = panel.findViewById(R.id.card_imageFront);

                Log.d("cDebug","blah");
                ((ImageView)imageView).setImageBitmap(BitmapFactory.decodeStream(new BufferedInputStream(cardFront.getImageStream())));
                imageView.setVisibility(View.VISIBLE);
            } else {
                ((TextView)panel.findViewById(R.id.card_textFront)).setText(cardFront.getText());
                panel.findViewById(R.id.card_textFront).setVisibility(View.VISIBLE);
                panel.findViewById(R.id.card_imageFront).setVisibility(View.GONE);
            }
            panel.findViewById(R.id.card_textBack).setVisibility(View.GONE);
        } else {
            CardSide cardBack = card.getBack();

            ((TextView)panel.findViewById(R.id.card_sideDisplay)).setText("Back");
            ((TextView)panel.findViewById(R.id.card_textBack)).setText(cardBack.getText());
            panel.findViewById(R.id.card_textBack).setVisibility(View.VISIBLE);

            panel.findViewById(R.id.card_textFront).setVisibility(View.GONE);
            panel.findViewById(R.id.card_imageFront).setVisibility(View.GONE);
        }
    }

    public View getPanel() {
        return panel;
    }
    public Card getCard() {
        return card;
    }
    public void flip() {
        card.flip();
        updateContents();
    }
}
