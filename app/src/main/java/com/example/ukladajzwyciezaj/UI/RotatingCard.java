package com.example.ukladajzwyciezaj.UI;

import android.content.Context;
import android.widget.ImageView;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.Card;
import com.example.ukladajzwyciezaj.CardInfuence;
import com.example.ukladajzwyciezaj.ForwardingAttack;
import com.example.ukladajzwyciezaj.Game;
import com.example.ukladajzwyciezaj.Player;
import com.example.ukladajzwyciezaj.R;
import com.example.ukladajzwyciezaj.SideAttack;

import java.net.ContentHandler;
import java.util.HashMap;


public class RotatingCard extends Card {
    private boolean possibilityRotation;
    private int afterRotationResource;

    public RotatingCard(CardInfuence left, CardInfuence right, CardInfuence top,
                        CardInfuence bottom, ImageView imageView, int imageResource,
                        int puntaction, int afterRotationResource, GameActivity gameActivity, Context context){
        super(left, right, top, bottom, imageView, imageResource, puntaction, gameActivity, context);
        this.possibilityRotation = true;
        this.afterRotationResource = afterRotationResource;
        this.setOnClick();
    }
    @Override
    public functionDelete getFunctionDelete() {
        functionDelete delete = new functionDelete() {
            @Override
            public void instructionDelete(ForwardingAttack informationAttack, HashMap<Integer, Card> positionKart, int position, int numCol, Player.ImageAdapter adapter) {
                if (possibilityRotation == false) {
                    informationAttack.RemoveAttack(position - 1, SideAttack.LEFT);
                    informationAttack.RemoveAttack(position + 1, SideAttack.RIGHT);
                    informationAttack.RemoveAttack(position + numCol, SideAttack.BOTTOM);
                    informationAttack.RemoveAttack(position - numCol, SideAttack.TOP);
                    positionKart.remove(position);
                    adapter.changeFirstImage(R.drawable.grafika_karty, position);
                } else {
                    possibilityRotation = false;
                    adapter.changeFirstImage(afterRotationResource, position);
                    CardInfuence top = informationAttack.getSetSideAttack().get(2).get(position - numCol);
                    CardInfuence right = informationAttack.getSetSideAttack().get(0).get(position+1);
                    CardInfuence left = informationAttack.getSetSideAttack().get(1).get(position-1);
                    CardInfuence bottom = informationAttack.getSetSideAttack().get(3).get(position + numCol);
                    informationAttack.SaveAttack(top,position + numCol, SideAttack.BOTTOM);
                    informationAttack.SaveAttack(right, position - 1, SideAttack.LEFT);
                    informationAttack.SaveAttack(left, position + 1, SideAttack.RIGHT);
                    informationAttack.SaveAttack(bottom, position - numCol, SideAttack.TOP);
                }
            }
        };
        return delete;
    }
}


