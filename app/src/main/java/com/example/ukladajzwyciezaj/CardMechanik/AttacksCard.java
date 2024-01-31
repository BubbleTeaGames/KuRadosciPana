package com.example.ukladajzwyciezaj.CardMechanik;

import com.example.ukladajzwyciezaj.Enum.CardInfuence;
import com.example.ukladajzwyciezaj.Enum.SideAttack;

public class AttacksCard {
    private CardInfuence rightAttack;
    private CardInfuence leftAttack;
    private CardInfuence topAttack;
    private CardInfuence bottomAttack;

    public AttacksCard(CardInfuence right, CardInfuence left, CardInfuence bottom, CardInfuence top){
        this.bottomAttack = bottom;
        this.leftAttack = left;
        this.rightAttack = right;
        this.topAttack = top;
    }

    public CardInfuence getForSide(SideAttack sideAttack){
        if (sideAttack == SideAttack.TOP) {
            return topAttack;
        } else if (sideAttack == SideAttack.LEFT) {
            return leftAttack;
        } else if (sideAttack == SideAttack.RIGHT) {
            return rightAttack;
        }else {
            return bottomAttack;
        }
    }
}
