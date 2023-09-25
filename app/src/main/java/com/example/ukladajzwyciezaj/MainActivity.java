package com.example.ukladajzwyciezaj;
import com.example.ukladajzwyciezaj.helpMetod;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;


public class MainActivity extends AppCompatActivity {
    HashSet<String> NamePlayers = new HashSet<>();
    int IDPlayers = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void OnCLickButtonStart(View v){
        if (NamePlayers.isEmpty()){
            Toast.makeText(getApplicationContext(), "Nie wprowadzono graczy", Toast.LENGTH_SHORT).show();
        } else if (NamePlayers.size() < 2) {
            Toast.makeText(getApplicationContext(), "Wprowadzono za mało graczy", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            ArrayList<String> NamePlayersList = new ArrayList<>(NamePlayers);
            intent.putStringArrayListExtra("NamePlayers",NamePlayersList);
            startActivity(intent);
        }
    }

    public void OnClickButtonAddPlayer(View v){
        IDPlayers = IDPlayers+1;
        LinearLayout linearLayout = findViewById(R.id.linearlayout);
        LinearLayout linearLayouthorizontal = new LinearLayout(this);
        int spacing = getResources().getDimensionPixelSize(R.dimen.button_spacing);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMarginEnd(spacing);

        TextView textView = helpMetod.getTextView(this);
        linearLayouthorizontal.addView(textView, layoutParams);

        EditText editText = helpMetod.getEditText(this, IDPlayers);
        linearLayouthorizontal.addView(editText, layoutParams);

        Button buttondelete = helpMetod.getButtonDelete(this, linearLayout, linearLayouthorizontal, IDPlayers, NamePlayers);
        linearLayouthorizontal.addView(buttondelete, layoutParams);

        Button buttonsubmit = helpMetod.getButtonsubmit(this, NamePlayers, IDPlayers);

        linearLayouthorizontal.addView(buttonsubmit, layoutParams);
        linearLayout.addView(linearLayouthorizontal);
    }

    public void OnclickButtonInstruction(View v){
        Intent intent = new Intent(MainActivity.this, InstructionActivity.class);
        startActivity(intent);
    }


}