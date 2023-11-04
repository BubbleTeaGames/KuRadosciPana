package com.example.ukladajzwyciezaj.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ukladajzwyciezaj.Card;
import com.example.ukladajzwyciezaj.CardMechanik.BasicCard;
import com.example.ukladajzwyciezaj.FunctionCard.FunctionCard;
import com.example.ukladajzwyciezaj.GameMechanik.Game;
import com.example.ukladajzwyciezaj.GameMechanik.Player;
import com.example.ukladajzwyciezaj.R;

import java.io.IOException;
import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private BasicCard chosen_card = null;
    private boolean buttonPawnEnable = false;
    private Game game;
    private Player CurrentVIewPlayer = null;
    private Player CurrentPlayer = null;
    private GridView cardContainers;
    private FunctionCard ChosenFunctionCard = null;

    public Game getGame() {
        return game;
    }

    public void setChosenKart(BasicCard chosen_card) {
        this.chosen_card = chosen_card;
    }

    public Player getCurrentPlayer() {
        return CurrentPlayer;
    }

    String[] opcje = {"Opcja 1", "Opcja 2", "Opcja 3", "Opcja 4"};

    private ArrayList<String> getPlayersNames() {
        Intent intent = getIntent();
        return intent.getStringArrayListExtra("NamePlayers");
    }

    public void setFunctionCard(FunctionCard functionCard) {
        this.ChosenFunctionCard = functionCard;
    }

    public GridView getCardContainers() {
        return cardContainers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        GridView cardsContainer = findViewById(R.id.gridview);
        LinearLayout listPlayer = findViewById(R.id.ListPlayer);
        this.cardContainers = cardsContainer;

        createGame();

        for (String playerName : getPlayersNames()) {
            Button buttonPlayer = new Button(this);
            buttonPlayer.setText(playerName);
            buttonPlayer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String NamePlayer = (String) buttonPlayer.getText();
                    for (Player elem : game.getPlayers()){
                        if (elem.getName() == NamePlayer){
                            cardsContainer.setAdapter(elem.getImageAdapter());
                            CurrentVIewPlayer = elem;
                        }
                    }
                }
            });
            listPlayer.addView(buttonPlayer);
        }

        cardsContainer.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public  void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getBaseContext(),"Wybrano kartę nr"+(position+1), Toast.LENGTH_SHORT).show();
                if (ChosenFunctionCard != null && game.getTurn().checkPossiblityMovement(CurrentPlayer)){
                    if (ChosenFunctionCard.actionFunctionCard(position, CurrentVIewPlayer)){
                        ImageView chosen_imageView = ChosenFunctionCard.getImageViewToCardsInHand();
                        CurrentPlayer.getCardInHeand().remove(ChosenFunctionCard);
                        LinearLayout linearLayout1 = findViewById(R.id.linearLayout);
                        linearLayout1.removeView(chosen_imageView);
                        game.getTurn().addMoveInTour(CurrentPlayer);
                        ChosenFunctionCard = null;
                    }
                }else if (chosen_card != null && game.getTurn().checkPossiblityMovement(CurrentPlayer) && chosen_card.possibilityCoveredCard(CurrentVIewPlayer, position)) {
                    ImageView chosen_imageView = chosen_card.getImageViewToCardsInHand();
                    CurrentPlayer.getCardInHeand().remove(chosen_card);
                    CurrentVIewPlayer.EnterCardToPlay(chosen_card, position);
                    CurrentVIewPlayer.getImageAdapter().notifyDataSetChanged();
                    LinearLayout linearLayout1 = findViewById(R.id.linearLayout);
                    linearLayout1.removeView(chosen_imageView);
                    game.getTurn().addMoveInTour(CurrentPlayer);
                    chosen_card = null;
                } else if ((buttonPawnEnable) && (CurrentPlayer == CurrentVIewPlayer) && (game.getTurn().checkPossibilityMovementPawn(CurrentPlayer))) {
                    CurrentPlayer.getPaws().movePaws(position, CurrentPlayer.getPositionKart());
                    CurrentPlayer.getImageAdapter().refreshAdapter();
                    game.getTurn().addMovePawnInTurn(CurrentPlayer);
                }
            }
        });

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        TextView textView = findViewById(R.id.CurrentPlayer);
        this.CurrentPlayer = game.getTurn().getNextPlayer();
        this.CurrentPlayer.SetViewLinearlayout(linearLayout);
        textView.setText(this.CurrentPlayer.getName());

        Spinner spinner = findViewById(R.id.spinner);
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



    private void createGame() {
        ArrayList<String> playerNames = getPlayersNames();
        try {
            game = new Game(this,this, playerNames, 10, 8);     // !!! wartości nie dynamiczne
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void completeCartInHeand(View v){
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        if(CurrentPlayer!=null) {
            CurrentPlayer.completeCartInHeand(game);
            CurrentPlayer.SetViewLinearlayout(linearLayout);
        }
    }

    public void OnclickButtonChancePawn(View v){
        this.buttonPawnEnable = !buttonPawnEnable;
        this.chosen_card = null;
    }

    public void OnclickButtonBattle(View v){
        game.getBattle().battle();
    }

    public void OnClickButtonNextPlayer(View v){
        buttonPawnEnable = false;
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        TextView textView = findViewById(R.id.CurrentPlayer);
        this.CurrentPlayer = game.getTurn().getNextPlayer();
        this.CurrentPlayer.SetViewLinearlayout(linearLayout);
        textView.setText(this.CurrentPlayer.getName());
    }
}