package com.example.ukladajzwyciezaj.CardMechanik;

import com.example.ukladajzwyciezaj.Enum.CardInfuence;
import com.example.ukladajzwyciezaj.Enum.SideAttack;

public class PositionsAttacked {
    private CardInfuence[] right;
    private CardInfuence[] left;
    private CardInfuence[] top;
    private CardInfuence[] bottom;

    PositionsAttacked(Integer quantityPlace){
        this.right = new CardInfuence[quantityPlace];
        this.left = new CardInfuence[quantityPlace];
        this.top = new CardInfuence[quantityPlace];
        this.bottom = new CardInfuence[quantityPlace];
    }

    public CardInfuence[] getForSide(SideAttack side){
        if (side == SideAttack.LEFT){
            return left;
        } else if (side == SideAttack.BOTTOM) {
            return bottom;
        } else if (side == SideAttack.TOP) {
            return top;
        } else {
            return right;
        }
    }
}
