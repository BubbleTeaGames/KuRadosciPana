package com.example.ukladajzwyciezaj;

import java.util.ArrayList;
import java.util.HashMap;

public class ForwardingAttack {

    private ArrayList<HashMap<Integer, InfluenceKart>> setSideAttack;

    public ArrayList<HashMap<Integer, InfluenceKart>> getSetSideAttack() {
        return setSideAttack;
    }

    public ForwardingAttack(){
        this.setSideAttack = new ArrayList<>();
        HashMap<Integer, InfluenceKart> RightAttack = new HashMap<>();
        HashMap<Integer, InfluenceKart> LeftAttack = new HashMap<>();
        HashMap<Integer, InfluenceKart> TopAttack = new HashMap<>();
        HashMap<Integer, InfluenceKart> BottomAttack = new HashMap<>();
        this.setSideAttack.add(RightAttack);
        this.setSideAttack.add(LeftAttack);
        this.setSideAttack.add(TopAttack);
        this.setSideAttack.add(BottomAttack);
    }

    public void SaveAttack(InfluenceKart powerAttack, int position, SideAttack side){
        switch (side){
            case TOP:
                setSideAttack.get(2).put(position, powerAttack);
                break;

            case LEFT:
                setSideAttack.get(1).put(position, powerAttack);
                break;

            case RIGHT:
                setSideAttack.get(0).put(position, powerAttack);
                break;

            case BOTTOM:
                setSideAttack.get(3).put(position, powerAttack);
                break;
        }
    }

    public void RemoveAttack(int position, SideAttack side){
        switch (side){
            case TOP:
                setSideAttack.get(2).remove(position);
                break;

            case LEFT:
                setSideAttack.get(1).remove(position);
                break;

            case RIGHT:
                setSideAttack.get(0).remove(position);
                break;

            case BOTTOM:
                setSideAttack.get(3).remove(position);
                break;
        }
    }

}
