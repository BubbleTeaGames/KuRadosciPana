package com.example.ukladajzwyciezaj;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

public class BattleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        Handler handler = new Handler();
        long delay = 2000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, delay);

    }
}