package com.csc331.flash_card_mania_app.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.csc331.flash_card_mania_app.R;

public class ButtonField {
    private View panel;

    public ButtonField(Context context, ViewGroup parent, String buttonText) {
        panel = LayoutInflater.from(context).inflate(R.layout.button_field, parent, false);

        ((Button)panel.findViewById(R.id.field_button)).setText(buttonText);
    }

    public View getPanel() {
        return panel;
    }
}
