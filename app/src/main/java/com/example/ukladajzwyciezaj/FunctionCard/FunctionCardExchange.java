package com.example.ukladajzwyciezaj.FunctionCard;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.CardMechanik.BasicCard;
import com.example.ukladajzwyciezaj.Enum.CardInfuence;
import com.example.ukladajzwyciezaj.Enum.SideAttack;
import com.example.ukladajzwyciezaj.GameMechanik.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import kotlin.Pair;

public class FunctionCardExchange extends FunctionCard{
    protected Pair<BasicCard, Integer> firstCardSelected;

    public void setFirstCardSelected(Pair<BasicCard, Integer> firstCardSelected) {
        this.firstCardSelected = firstCardSelected;
    }

    public FunctionCardExchange(Context context, GameActivity gameActivity, ImageView mainView){
        super(context, gameActivity, mainView);
        this.firstCardSelected = null;
    }

    public boolean firstSkillAction(int position1, Player player){
        if (firstCardSelected == null && player.getPositionKart().containsKey(position1)){
            setFirstCardSelected(new Pair<>(player.getPositionKart().get(position1), position1));
        }else if (player.getPositionKart().containsKey(position1)){
            swapCard(position1, player);
            return true;
        }
        return false;
    }

    @Override
    void cancelCard() {
        this.firstCardSelected = null;
    }

    private void swapCard(int position1, Player player){
        Pair<BasicCard, Integer> SecondCardSelected = new Pair<>(player.getPositionKart().get(position1), position1);
        player.EnterCardToPlay(firstCardSelected.getFirst(), SecondCardSelected.getSecond());
        player.EnterCardToPlay(SecondCardSelected.getFirst(), firstCardSelected.getSecond());
        player.getImageAdapter().refreshAdapter();
        firstCardSelected = null;
    }

}


