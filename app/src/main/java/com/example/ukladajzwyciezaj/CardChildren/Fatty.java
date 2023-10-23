package com.example.ukladajzwyciezaj.CardChildren;

import android.content.Context;
import android.widget.ImageView;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.Card;
import com.example.ukladajzwyciezaj.CardInfuence;

import java.util.Calendar;

public class Fatty extends Card {
    public Fatty(CardInfuence left, CardInfuence right, CardInfuence top, CardInfuence bottom,
                              ImageView imageView, int imageResource, int puntaction, GameActivity gameActivity, Context context, boolean weakling){
        super(left, right, top, bottom, imageView, imageResource, puntaction, gameActivity, context, weakling);
    }
}
