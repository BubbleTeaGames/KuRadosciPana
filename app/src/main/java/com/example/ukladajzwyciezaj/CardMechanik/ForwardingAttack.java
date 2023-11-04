package com.example.ukladajzwyciezaj.CardMechanik;

import com.example.ukladajzwyciezaj.Enum.CardInfuence;
import com.example.ukladajzwyciezaj.Enum.SideAttack;

public class ForwardingAttack {

    private PositionsAttacked setSideAttack;
    private int numCol;
    private int allPosition;

    public PositionsAttacked getSetSideAttack() {
        return setSideAttack;
    }

    public ForwardingAttack(int numCol){
        this.setSideAttack = new PositionsAttacked(80);
        this.allPosition = 80;
        this.numCol = numCol;
    }

    public void SaveAttackFrom(CardInfuence powerAttack, int position, SideAttack side){
        Integer positionReceivingAttack = getAttackPosition(side, position);
        if (positionReceivingAttack < 80 && positionReceivingAttack >= 0){
            setSideAttack.getForSide(getOppositeSide(side))[positionReceivingAttack] = powerAttack;
        }
    }

    public void RemoveAttack(int position, SideAttack side){
        Integer positionReceivingAttack = getAttackPosition(side, position);
        if (positionReceivingAttack < 80 && positionReceivingAttack >= 0){
            setSideAttack.getForSide(getOppositeSide(side))[positionReceivingAttack] = null;
        }
    }

    public int getAttackPosition(SideAttack side, int position){
        Integer positionReceivingAttack;
        if (side == SideAttack.LEFT) {
            positionReceivingAttack = position-1;
        } else if (side == SideAttack.RIGHT) {
            positionReceivingAttack = position+1;
        } else if (side == SideAttack.TOP) {
            positionReceivingAttack = position-numCol;
        } else{
            positionReceivingAttack = position+numCol;
        }
        return positionReceivingAttack;
    }

    public SideAttack getOppositeSide(SideAttack side){
        if (side == SideAttack.LEFT) {
            return SideAttack.RIGHT;
        } else if (side == SideAttack.RIGHT) {
            return SideAttack.LEFT;
        } else if (side == SideAttack.TOP) {
            return SideAttack.BOTTOM;
        } else{
            return SideAttack.TOP;
        }
    }

}
