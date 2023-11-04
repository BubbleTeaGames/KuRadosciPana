package com.example.ukladajzwyciezaj.CardChildren;

import android.content.Context;
import android.widget.ImageView;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.CardMechanik.BasicCard;
import com.example.ukladajzwyciezaj.Enum.CardInfuence;

import java.util.HashMap;

public class ClaustrophobiaBasicCard extends BasicCard {
    public ClaustrophobiaBasicCard(CardInfuence left, CardInfuence right, CardInfuence top, CardInfuence bottom,
                                   ImageView imageView, int imageResource, int puntaction, GameActivity gameActivity, Context context, boolean weakling){
        super(left, right, top, bottom, imageView, imageResource, puntaction, gameActivity, context, weakling);
    }

    public boolean CheckIfHeIsCornered2(HashMap<Integer, BasicCard> positionCards, Integer numCol, Integer numRow, Integer position){
        Integer rightPlace = position+1;
        Integer leftPlace = position-1;
        Integer topPlace = position - numCol;
        Integer bottomPlace = position + numCol;
        Integer row = (int) getRowPlaceInMatrix(position, numCol, numRow);
        Integer lastRightPosition = (row*numCol)-1;
        Integer firstLeftPosition = (row * numCol);
        if (!positionCards.containsKey(rightPlace) && !(position == lastRightPosition)){
            return false;
        }
        if ((!positionCards.containsKey(leftPlace) && !(position == firstLeftPosition))){
            return false;
        }
        if ((!positionCards.containsKey(topPlace) && !(row == 1))){
            return false;
        }
        if ((!positionCards.containsKey(bottomPlace)) && !(row == numRow)){
            return false;
        }
        return true;
    }

    private double getRowPlaceInMatrix(Integer position, int numCol, int numRow){
        double RowPawn = (position)/(numCol) + 1;
        return Math.ceil(RowPawn);
    }
}
