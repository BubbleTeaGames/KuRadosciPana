package com.example.ukladajzwyciezaj.CardChildren;

import android.content.Context;
import android.widget.ImageView;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.CardMechanik.BasicCard;
import com.example.ukladajzwyciezaj.Enum.CardInfuence;

public class Fatty extends BasicCard {
    public Fatty(CardInfuence left, CardInfuence right, CardInfuence top, CardInfuence bottom,
                              ImageView imageView, int imageResource, int puntaction, GameActivity gameActivity, Context context){
        super(left, right, top, bottom, imageView, imageResource, puntaction, gameActivity, context, false);
    }
}
