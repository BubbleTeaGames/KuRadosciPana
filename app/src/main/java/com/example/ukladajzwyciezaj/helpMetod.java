package com.example.ukladajzwyciezaj;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.style.IconMarginSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;

import java.util.ArrayList;
import java.util.HashSet;

public class helpMetod {
    public static Button getButtonDelete(Context context, LinearLayout linearLayout, LinearLayout linearLayouthorizontal, int IdPlayers, HashSet<String> NamedPlayers) {
        Button button = new Button(context);
        button.setText("delete");
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(Color.RED); // Ustaw kolor tła przycisku
        button.setBackground(gradientDrawable);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) ((Activity) context).findViewById(IdPlayers);
                String text = name.getText().toString().trim();
                NamedPlayers.remove(text);
                linearLayout.removeView(linearLayouthorizontal);

            }
        });
        return button;
    }

    public static Button getButtonsubmit(Context context, HashSet<String> NamePlayers, int IdPlayers) {
        Button button = new Button(context);
        button.setText("submit");
        //int pixels = (int) TypedValue.applyDimension(
        //        TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()
        //);
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pixels, LinearLayout.LayoutParams.WRAP_CONTENT);
        //button.setLayoutParams(params);
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(Color.GREEN); // Ustaw kolor tła przycisku
        button.setBackground(gradientDrawable);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText name = (EditText) ((Activity) context).findViewById(IdPlayers);
                String text = name.getText().toString().trim();
                if (!NamePlayers.contains(text)) {
                    if (text.length() == 0) {
                        Toast.makeText(context.getApplicationContext(), "Nie wprowadzono nazwy", Toast.LENGTH_SHORT).show();
                    } else {
                        NamePlayers.add(text);
                        gradientDrawable.setColor(Color.GRAY);
                        button.setBackground(gradientDrawable);
                        name.setEnabled(false);
                    }
                } else {
                    Toast.makeText(context.getApplicationContext(), "Ta nazwa już istnieje", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return button;
    }


    public static EditText getEditText(Context context, int quantityEditText) {
        EditText editText = new EditText(context);
        editText.setId(quantityEditText);
        int maxLength = 15;
        InputFilter[] filters = new InputFilter[]{new InputFilter.LengthFilter(maxLength)};
        editText.setFilters(filters);
        return editText;
    }

    public static TextView getTextView(Context context) {
        TextView bigDotTextVIew = new TextView(context);
        bigDotTextVIew.setText("\u2022");
        bigDotTextVIew.setTextSize(30);
        return bigDotTextVIew;
    }

    public static SideAttack getSideToCheckDefense(Context context, Integer integer) {
        if (integer == 0) {
            return SideAttack.LEFT;
        } else if (integer == 1) {
            return SideAttack.RIGHT;
        } else if (integer == 2) {
            return SideAttack.BOTTOM;
        } else {
            return SideAttack.TOP;
        }
    }

}
