package com.example.ukladajzwyciezaj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Turn {
    private int actualTurn;
    private int maxMoves;
    private HashMap<Player, Integer> queueMoves;
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    public Turn(ArrayList<Player> players, int maxMoves){
        this.queueMoves = new HashMap<>();
        this.actualTurn = 0;
        for (Player player : players){
            this.queueMoves.put(player, 0);
        }
        this.maxMoves = maxMoves;
        this.currentPlayerIndex = 0;
        this.players = players;
    }
    public void nextTour(ArrayList<Player> players){
        this.actualTurn += 1;
        for (Player player : players){
            this.queueMoves.put(player, 0);
        }
    }

    public void addMoveInTour(Player makingMove){
        queueMoves.put(makingMove, queueMoves.get(makingMove)+1);
    }

    public boolean checkPossiblityMovement(Player player){
        if (this.queueMoves.get(player) >= this.maxMoves){
            return false;
        }else{
            return true;
        }
    }

    public void nextTurn(){
        this.actualTurn = this.actualTurn+1;
        for (Map.Entry<Player, Integer> entry : this.queueMoves.entrySet()){
            Player key = entry.getKey();
            this.queueMoves.put(key, 0);
        }
    }

    public Player getNextPlayer(){
        if (this.currentPlayerIndex >= this.players.size()-1){
            this.currentPlayerIndex = 0;
            this.nextTurn();
        }else {
            this.currentPlayerIndex = this.currentPlayerIndex +1;
        }
        return this.players.get(this.currentPlayerIndex);
    }



}
