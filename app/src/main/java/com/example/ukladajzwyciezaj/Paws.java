package com.example.ukladajzwyciezaj;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Paws {
    private Integer actualPositoin;
    private Integer quantityRow;
    private Integer quantityCol;

    private Context context;

    public Paws(Context context,int quantityRow, int quantityCol){
        this.context = context;
        this.quantityCol = quantityCol;
        this.quantityRow = quantityRow;
    }

    public Integer getActualPositoin() {
        return actualPositoin;
    }

    public boolean pawsInBoard(){
        if (actualPositoin==null){
            return false;
        }else {
            return true;
        }
    }

    public void movePaws(int position, HashMap<Integer, Card> positionCards){
        Integer newPlace;
        if (actualPositoin == 0){
            newPlace = startPaws(position);
        }else {
            newPlace = movement(position, positionCards);
        }
        if (newPlace != null){
            actualPositoin = newPlace;
        }
    }

    private Integer startPaws(int startPosition){
        int firstgoodfiled = (quantityCol * quantityRow) - quantityCol - 1;
        int lastgoodfiled = (quantityCol * quantityRow)-1;
        if (startPosition > firstgoodfiled && startPosition < lastgoodfiled){
            return startPosition;
        }
        else {
            Toast.makeText(context,"pinek zaczyna od dołu", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    private ArrayList<Integer> getAllowedPosition(){
        ArrayList<Integer> allowedPosition = new ArrayList<>();
        allowedPosition.add(this.actualPositoin+1);
        allowedPosition.add(this.actualPositoin-1);
        allowedPosition.add(this.actualPositoin+this.quantityCol);
        allowedPosition.add(this.actualPositoin-this.quantityCol);
        return allowedPosition;
    }

    private Integer movement(int positoinToMove, HashMap<Integer, Card> positionCards){
        ArrayList<Integer> allowedPosition = getAllowedPosition();
        if (allowedPosition.contains(positoinToMove)){
            if (positionCards.containsKey(positoinToMove)) {
                return positoinToMove;
            }else {
                Toast.makeText(context, "tu nie ma karty", Toast.LENGTH_SHORT).show();
                return null;
            }
        }else {
            Toast.makeText(context, "złe pole", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

}
