package com.example.ukladajzwyciezaj.CardChildren;

import android.content.Context;
import android.widget.ImageView;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.CardMechanik.BasicCard;
import com.example.ukladajzwyciezaj.Enum.CardInfuence;

public class Kamikaze extends BasicCard {
    public Kamikaze(CardInfuence left, CardInfuence right, CardInfuence bottom, CardInfuence top,
                    ImageView imageView, int imageResource, int puntaction, GameActivity gameActivity, Context context, boolean weakling){
        super(left, right, bottom, top, imageView, imageResource, puntaction, gameActivity, context, weakling);
    }

}
