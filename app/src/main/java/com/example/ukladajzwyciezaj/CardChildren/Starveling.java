package com.example.ukladajzwyciezaj.CardChildren;

import android.content.Context;
import android.widget.ImageView;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.CardMechanik.BasicCard;
import com.example.ukladajzwyciezaj.Enum.CardInfuence;
import com.example.ukladajzwyciezaj.Enum.SideAttack;

import java.util.ArrayList;
import java.util.List;

public class Starveling extends BasicCard {
    public Starveling(CardInfuence left, CardInfuence right, CardInfuence top, CardInfuence bottom,
                      ImageView imageView, int imageResource, int puntaction, GameActivity gameActivity, Context context, boolean weakling){
        super(left, right, top, bottom, imageView, imageResource, puntaction, gameActivity, context, weakling);
    }
    public List<Integer> getDestroyPosition(int position, int numCol){
        List<Integer> pointedCard = new ArrayList<>();
        if (whetherTheCardAttacksThisSquare(attacksCard.getForSide(SideAttack.TOP))){
            pointedCard.add(position - numCol);
        }
        if (whetherTheCardAttacksThisSquare(attacksCard.getForSide(SideAttack.BOTTOM))){
            pointedCard.add(position + numCol);
        }
        if (whetherTheCardAttacksThisSquare(attacksCard.getForSide(SideAttack.LEFT))){
            pointedCard.add(position - 1);
        }
        if (whetherTheCardAttacksThisSquare(attacksCard.getForSide(SideAttack.RIGHT))){
            pointedCard.add(position + 1);
        }
        return pointedCard;
    }

    private boolean whetherTheCardAttacksThisSquare(CardInfuence side){
        if(side == CardInfuence.ATTACK || side == CardInfuence.DOUBLE_ATTACK || side == CardInfuence.TRIPLE_ATTACK){
            return true;
        }else {
            return false;
        }
    }
}
