package com.example.ukladajzwyciezaj.CardChildren;

import android.content.Context;
import android.widget.ImageView;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.CardMechanik.BasicCard;
import com.example.ukladajzwyciezaj.Enum.CardInfuence;
import com.example.ukladajzwyciezaj.CardMechanik.ForwardingAttack;
import com.example.ukladajzwyciezaj.GameMechanik.Player;
import com.example.ukladajzwyciezaj.R;
import com.example.ukladajzwyciezaj.Enum.SideAttack;

import java.util.HashMap;


public class RotatingBasicCard extends BasicCard {
    private boolean possibilityRotation;
    private int afterRotationResource;

    public RotatingBasicCard(CardInfuence left, CardInfuence right, CardInfuence top,
                             CardInfuence bottom, ImageView imageView, int imageResource,
                             int puntaction, int afterRotationResource, GameActivity gameActivity, Context context, boolean weakling){
        super(left, right, top, bottom, imageView, imageResource, puntaction, gameActivity, context, weakling);
        this.possibilityRotation = true;
        this.afterRotationResource = afterRotationResource;
        this.setOnClick();
    }
    @Override
    public functionDelete getFunctionDelete() {
        functionDelete delete = new functionDelete() {
            @Override
            public void instructionDelete(ForwardingAttack informationAttack, HashMap<Integer, BasicCard> positionKart, int position, int numCol, Player.ImageAdapter adapter) {
                if (possibilityRotation == false) {
                    informationAttack.RemoveAttack(position, SideAttack.LEFT);
                    informationAttack.RemoveAttack(position, SideAttack.RIGHT);
                    informationAttack.RemoveAttack(position, SideAttack.BOTTOM);
                    informationAttack.RemoveAttack(position, SideAttack.TOP);
                    positionKart.remove(position);
                    adapter.changeFirstImage(R.drawable.grafika_karty, position);
                } else {
                    possibilityRotation = false;
                    adapter.changeFirstImage(afterRotationResource, position);
                    informationAttack.SaveAttackFrom(attacksCard.getForSide(SideAttack.TOP), position, SideAttack.BOTTOM);
                    informationAttack.SaveAttackFrom(attacksCard.getForSide(SideAttack.RIGHT), position, SideAttack.LEFT);
                    informationAttack.SaveAttackFrom(attacksCard.getForSide(SideAttack.LEFT), position, SideAttack.RIGHT);
                    informationAttack.SaveAttackFrom(attacksCard.getForSide(SideAttack.BOTTOM), position, SideAttack.TOP);
                }
            }
        };
        return delete;
    }
}


