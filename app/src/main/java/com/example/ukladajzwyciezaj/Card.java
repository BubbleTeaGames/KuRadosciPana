package com.example.ukladajzwyciezaj;

import android.view.View;
import android.widget.ImageView;

import com.example.ukladajzwyciezaj.CardMechanik.BasicCard;
import com.example.ukladajzwyciezaj.GameMechanik.Player;

public interface Card {
    void setOnClick();
    Card setOnClickListener(View.OnClickListener listener);

    ImageView getImageViewToCardsInHand();

}
