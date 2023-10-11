package com.example.ukladajzwyciezaj.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ukladajzwyciezaj.R;

import java.util.ArrayList;

public class FinishActivity extends AppCompatActivity {
    private ArrayList<String> getWinningPlayer() {
        Intent intent = getIntent();
        return intent.getStringArrayListExtra("Ranking");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        ArrayList<String> winningPlayer = getWinningPlayer();

        TextView winner = findViewById(R.id.firstPlace);
        winner.setText(winningPlayer.remove(winningPlayer.size()-1));

        TextView secondPlace = findViewById(R.id.secondPlace);
        secondPlace.setText(winningPlayer.remove(winningPlayer.size()-1));

        if (!winningPlayer.isEmpty()){
            TextView thridPlace = findViewById(R.id.thridPlace);
            thridPlace.setText(winningPlayer.remove(winningPlayer.size()-1));
        }
    }
}