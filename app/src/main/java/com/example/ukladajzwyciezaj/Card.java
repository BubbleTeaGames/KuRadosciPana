package com.example.ukladajzwyciezaj;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ukladajzwyciezaj.Activites.GameActivity;

import java.util.HashMap;

public class Card {
    //private HashMap<SideAttack, CardInfuence> valueAttack;
    private ImageView imageView;
    private int ImageResource;
    private int puntaction;
    private GameActivity gameActivity;
    protected Context context;
    private boolean isWeakling;
    protected AttacksCard attacksCard;

    public Card(CardInfuence left, CardInfuence right, CardInfuence bottom, CardInfuence top,
                ImageView imageView, int imageResource, int puntaction, GameActivity gameActivity, Context context, boolean weakling){
        this.ImageResource = imageResource;
        this.attacksCard = new AttacksCard(right, left, bottom, top);
        this.imageView = imageView;
        this.puntaction = puntaction;
        this.gameActivity = gameActivity;
        this.context = context;
        this.setOnClick();
        this.isWeakling = weakling;
    }

    public AttacksCard getAttacksCard() {
        return attacksCard;
    }

    public boolean isWeakling() {
        return isWeakling;
    }

    public int getPuntaction() {
        return puntaction;
    }

    public ImageView getImageViewToBoard() {
        return imageView;
    }
    public ImageView getImageViewToCardsInHand(){
        return imageView;
    }
    @FunctionalInterface
    protected interface functionDelete{
        void instructionDelete(ForwardingAttack informationAttack, HashMap<Integer, Card> positionKart, int position, int numCol, Player.ImageAdapter adapter);
    }
    public functionDelete getFunctionDelete(){
        functionDelete delete = new functionDelete() {
            @Override
            public void instructionDelete(ForwardingAttack informationAttack, HashMap<Integer, Card> positionKart, int position, int numCol, Player.ImageAdapter adapter) {
                informationAttack.RemoveAttack(position-1, SideAttack.LEFT);
                informationAttack.RemoveAttack(position+1, SideAttack.RIGHT);
                informationAttack.RemoveAttack(position+numCol, SideAttack.BOTTOM);
                informationAttack.RemoveAttack(position-numCol, SideAttack.TOP);
                positionKart.remove(position);
                adapter.changeFirstImage(R.drawable.grafika_karty, position);
            }
        };
        return delete;
    }

    public void setOnClick(){
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Wybrano kartę do wstawienia", Toast.LENGTH_SHORT).show();
                gameActivity.setChosenKart(Card.this);
            }
        });
    }

    public Card setOnClickListener(View.OnClickListener listener) {
        imageView.setOnClickListener(listener);
        return this;
    }

    public boolean possibilityCoveredCard(Player CurrentVIewPlayer, int position){
        return !CurrentVIewPlayer.getPositionKart().containsKey(position);
    }
}
