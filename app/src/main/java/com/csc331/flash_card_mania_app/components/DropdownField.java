package com.csc331.flash_card_mania_app.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.csc331.flash_card_mania_app.Card;
import com.csc331.flash_card_mania_app.R;

import java.util.ArrayList;

public class DropdownField {
    private View panel;
    private Context context;
    private ArrayAdapter<String> adapter;

    public DropdownField(Context context, ViewGroup parent, String leftText, ArrayList<String> options) {
        this.context = context;
        panel = LayoutInflater.from(context).inflate(R.layout.dropdown_field, parent, false);

        ((TextView)panel.findViewById(R.id.field_leftText)).setText(leftText);
        final Spinner difficultyDropdown = panel.findViewById(R.id.field_dropdown);
        adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyDropdown.setAdapter(adapter);
        difficultyDropdown.setSelection(0);
    }

    public View getPanel() {
        return panel;
    }
    public String getSelectedOption() {
        return ((Spinner)panel.findViewById(R.id.field_dropdown)).getSelectedItem().toString();
    }

    public void setOptions(ArrayList<String> options) {
        final Spinner difficultyDropdown = panel.findViewById(R.id.field_dropdown);
        adapter = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultyDropdown.setAdapter(adapter);
        difficultyDropdown.setSelection(0);
    }
    public void setSelectedOption(String value) {
        ((Spinner)panel.findViewById(R.id.field_dropdown)).setSelection(adapter.getPosition(value));
    }
}
