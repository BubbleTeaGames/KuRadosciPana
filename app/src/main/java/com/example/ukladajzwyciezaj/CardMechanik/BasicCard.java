package com.example.ukladajzwyciezaj.CardMechanik;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.Card;
import com.example.ukladajzwyciezaj.Enum.CardInfuence;
import com.example.ukladajzwyciezaj.Enum.SideAttack;
import com.example.ukladajzwyciezaj.GameMechanik.Player;
import com.example.ukladajzwyciezaj.R;

import java.util.HashMap;

public class BasicCard implements Card {
    //private HashMap<SideAttack, CardInfuence> valueAttack;
    private ImageView imageView;
    private int ImageResource;
    private int puntaction;
    private GameActivity gameActivity;
    protected Context context;
    private boolean isWeakling;
    protected AttacksCard attacksCard;

    public BasicCard(CardInfuence left, CardInfuence right, CardInfuence bottom, CardInfuence top,
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
    @Override
    public ImageView getImageViewToCardsInHand(){
        return imageView;
    }
    @FunctionalInterface
    public interface functionDelete{
        void instructionDelete(ForwardingAttack informationAttack, HashMap<Integer, BasicCard> positionKart, int position, int numCol, Player.ImageAdapter adapter);
    }
    public functionDelete getFunctionDelete(){
        functionDelete delete = new functionDelete() {
            @Override
            public void instructionDelete(ForwardingAttack informationAttack, HashMap<Integer, BasicCard> positionKart, int position, int numCol, Player.ImageAdapter adapter) {
                informationAttack.RemoveAttack(position, SideAttack.LEFT);
                informationAttack.RemoveAttack(position, SideAttack.RIGHT);
                informationAttack.RemoveAttack(position, SideAttack.BOTTOM);
                informationAttack.RemoveAttack(position, SideAttack.TOP);
                positionKart.remove(position);
                adapter.changeFirstImage(R.drawable.grafika_karty, position);
            }
        };
        return delete;
    }

    @Override
    public void setOnClick(){
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"Wybrano kartę do wstawienia", Toast.LENGTH_SHORT).show();
                gameActivity.setChosenKart(BasicCard.this);
            }
        });
    }

    @Override
    public BasicCard setOnClickListener(View.OnClickListener listener) {
        imageView.setOnClickListener(listener);
        return this;
    }

    public boolean possibilityCoveredCard(Player CurrentVIewPlayer, int position){
        return !CurrentVIewPlayer.getPositionKart().containsKey(position);
    }
}
