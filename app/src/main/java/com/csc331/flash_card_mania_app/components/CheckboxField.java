package com.csc331.flash_card_mania_app.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.csc331.flash_card_mania_app.R;

import java.util.ArrayList;

public class CheckboxField {
    private View panel;

    public CheckboxField(Context context, ViewGroup parent, String leftText) {
        panel = LayoutInflater.from(context).inflate(R.layout.checkbox_field, parent, false);

        ((TextView)panel.findViewById(R.id.field_leftText)).setText(leftText);
    }

    public View getPanel() {
        return panel;
    }
    public Boolean getCheckedValue() {
        return ((Switch)panel.findViewById(R.id.field_switch)).isChecked();
    }

    public void setCheckedValue(Boolean value) {
        ((Switch)panel.findViewById(R.id.field_switch)).setChecked(value);
    }
}
