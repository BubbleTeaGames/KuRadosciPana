package com.example.ukladajzwyciezaj;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import kotlin.Pair;

public class GameActivity extends AppCompatActivity {
    Kart chosen_kart = null;
    Game game;
    private Player CurrentVIewPlayer = null;
    private Player CurrentPlayer = null;

    public Kart getChosen_kart() {
        return chosen_kart;
    }

    public void setChosen_kart(Kart chosen_kart) {
        this.chosen_kart = chosen_kart;
    }

    String[] opcje = {"Opcja 1", "Opcja 2", "Opcja 3", "Opcja 4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        ArrayList<String> NamePlayersList = intent.getStringArrayListExtra("NamePlayers");

        try {
            game = new Game(this,this, NamePlayersList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GridView gridView = findViewById(R.id.gridview);
        LinearLayout listPlayer = findViewById(R.id.ListPlayer);

        for (int i = 0; i < NamePlayersList.size(); i++) {
            Button buttonPlayer = new Button(this);
            //buttonPlayer.setTag(NamePlayersList.get(i));
            buttonPlayer.setText(NamePlayersList.get(i));
            buttonPlayer.setOnClickListener(new View.OnClickListener() {
                //Player ActualPlayer = null;
                @Override
                public void onClick(View v) {

                    String NamePlayer = (String) buttonPlayer.getText();
                    for (Player elem : game.getPlayers()){
                        if (elem.getName() == NamePlayer){
                            gridView.setAdapter(elem.getImageAdapter());
                            //ActualPlayer = elem;
                            CurrentVIewPlayer = elem;
                        }
                    }
                }
            });
            listPlayer.addView(buttonPlayer);
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public  void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getBaseContext(),"Wybrano kartę nr"+(position+1), Toast.LENGTH_SHORT).show();
                if (chosen_kart != null) {
                    ImageView chosen_imageView = chosen_kart.getImageView();
                    int newPosition = CurrentVIewPlayer.EnterCardToPlay(gridView, chosen_kart,position);
                    ImageView placeforCard = (ImageView) gridView.getAdapter().getView(newPosition, null, gridView);
                    placeforCard.setImageDrawable(chosen_imageView.getDrawable());
                    LinearLayout linearLayout1 = findViewById(R.id.linearLayout);
                    linearLayout1.removeView(chosen_imageView);

                    chosen_kart = null;
                }
            }
        });

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        TextView textView = findViewById(R.id.CurrentPlayer);
        this.CurrentPlayer = game.getNextPlayer();
        this.CurrentPlayer.SetViewLinearlayout(linearLayout);
        textView.setText(this.CurrentPlayer.getName());
        //LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        //this.CurrentPlayer.SetViewLinearlayout(linearLayout);

        /*
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        for (int j=0; j<3; j++){
            ImageView localView = game.getPileOfKart().getRandomKartToGame().getImageView();
            linearLayout.addView(localView);
        }

         */

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opcje);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String wybranaOpcja = opcje[position];
                Toast.makeText(getApplicationContext(), "Wybrano: " + wybranaOpcja, Toast.LENGTH_SHORT).show();
                if (position == 1){
                    Intent intent = new Intent(GameActivity.this, InstructionActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Obsługa przypadku, gdy nic nie jest wybrane
            }
        });
    }

    public void completeCartInHeand(View v){
        /*
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        int Childcount = linearLayout.getChildCount();
        if (Childcount < 2 ) {
            while (Childcount < 3) {
                if (game.getPileOfKart().size() < 1){
                    Toast.makeText(getBaseContext(),"Koniec kart w tali", Toast.LENGTH_SHORT).show();
                    break;
                }
                ImageView localView = game.getPileOfKart().getRandomKartToGame().getImageView();
                linearLayout.addView(localView);
                Childcount = linearLayout.getChildCount();
            }
        }else{
            Toast.makeText(getBaseContext(),"Nie możesz uzupełnić kart", Toast.LENGTH_SHORT).show();
        }
        */
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        if(CurrentVIewPlayer!=null) {
            CurrentVIewPlayer.completeCartInHeand(game);
            CurrentVIewPlayer.SetViewLinearlayout(linearLayout);
        }
    }

    public void OnclickButtonEndTurn(View v){
        ArrayList<Pair<Player, Integer>> a = game.Buttle();
        //CurrentPlayer.getImageAdapter().changeFirstImage(R.drawable.kart_left_attack_right_defence, 0);
        //LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        //linearLayout.removeAllViews();
        //Toast.makeText(getBaseContext(),"test", Toast.LENGTH_SHORT).show();
    }

    public void OnClickButtonNextPlayer(View v){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        TextView textView = findViewById(R.id.CurrentPlayer);
        this.CurrentPlayer = game.getNextPlayer();
        this.CurrentPlayer.SetViewLinearlayout(linearLayout);
        textView.setText(this.CurrentPlayer.getName());
    }


}