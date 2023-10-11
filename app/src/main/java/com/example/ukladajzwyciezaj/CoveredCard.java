package com.example.ukladajzwyciezaj;

import android.content.Context;
import android.widget.ImageView;

import com.example.ukladajzwyciezaj.Activites.GameActivity;

public class CoveredCard extends Card{
    private int resourceCovered;
    public CoveredCard(CardInfuence left, CardInfuence right, CardInfuence top,
                       CardInfuence bottom, ImageView imageView, int imageResource,
                       int puntaction, int ResourceCovered, GameActivity gameActivity, Context context){
        super(left, right, top, bottom, imageView, imageResource, puntaction, gameActivity, context);
        this.resourceCovered = ResourceCovered;
    }

    @Override
    public ImageView getImageViewToBoard(){
        ImageView cardsVIewAfterInsertion = new ImageView(context);
        cardsVIewAfterInsertion.setImageResource(resourceCovered);
        return cardsVIewAfterInsertion;
    }

}
