package com.example.ukladajzwyciezaj.CardChildren;

import android.content.Context;
import android.widget.ImageView;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.Card;
import com.example.ukladajzwyciezaj.CardInfuence;

public class CoveredCard extends Card {
    private ImageView covered;
    public CoveredCard(CardInfuence left, CardInfuence right, CardInfuence top,
                       CardInfuence bottom, ImageView imageView, int imageResource,
                       int puntaction, int ResourceCovered, GameActivity gameActivity, Context context, boolean weakling){
        super(left, right, top, bottom, imageView, imageResource, puntaction, gameActivity, context, weakling);
        ImageView i = new ImageView(context);
        i.setImageResource(ResourceCovered);
        this.covered = i;
    }

    @Override
    public ImageView getImageViewToBoard(){
        return covered;
    }

}
