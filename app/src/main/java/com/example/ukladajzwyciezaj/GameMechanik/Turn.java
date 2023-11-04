package com.example.ukladajzwyciezaj.GameMechanik;

import android.widget.GridView;

import com.example.ukladajzwyciezaj.CardChildren.ClaustrophobiaBasicCard;
import com.example.ukladajzwyciezaj.CardChildren.Starveling;
import com.example.ukladajzwyciezaj.CardMechanik.BasicCard;
import com.example.ukladajzwyciezaj.CardMechanik.Gravitation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Turn {
    private int actualTurn;
    private int maxMoves;
    private int maxPawnMoves;
    private HashMap<Player, Integer> availableCardMoves;
    private HashMap<Player, Integer> availablePawnMoves;
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private Gravitation gravitation;
    public Turn(ArrayList<Player> players, int maxMoves, int maxPawnMoves, GridView cardContainer){
        this.availableCardMoves = new HashMap<>();
        this.availablePawnMoves = new HashMap<>();
        this.actualTurn = 0;
        for (Player player : players){
            this.availableCardMoves.put(player, 0);
            this.availablePawnMoves.put(player, 0);
        }
        this.gravitation = new Gravitation(cardContainer);
        this.maxMoves = maxMoves;
        this.currentPlayerIndex = 0;
        this.players = players;
        this.maxPawnMoves = maxPawnMoves;
    }
    public void nextTour(ArrayList<Player> players){
        this.actualTurn += 1;
        for (Player player : players){
            this.availableCardMoves.put(player, 0);
        }
    }

    public void addMoveInTour(Player makingMove){
        availableCardMoves.put(makingMove, availableCardMoves.get(makingMove)+1);
    }

    public void  addMovePawnInTurn(Player makingMove){
        availablePawnMoves.put(makingMove, availablePawnMoves.get(makingMove)+1);
    }

    public boolean checkPossibilityMovementPawn(Player player){
        if (this.availablePawnMoves.get(player) >= this.maxPawnMoves){
            return false;
        }else {
            return true;
        }
    }

    public boolean checkPossiblityMovement(Player player){
        if (this.availableCardMoves.get(player) >= this.maxMoves){
            return false;
        }else{
            return true;
        }
    }

    public void nextTurn(){
        this.actualTurn = this.actualTurn+1;
        for (Map.Entry<Player, Integer> entry : this.availableCardMoves.entrySet()){
            Player key = entry.getKey();
            this.availableCardMoves.put(key, 0);
            this.availablePawnMoves.put(key, 0);
        }
    }

    public Player getNextPlayer(){
        checkAfterMovePlayer();
        gravitation.gravitationWeakling(players.get(currentPlayerIndex));

        if (this.currentPlayerIndex >= this.players.size()-1){
            this.currentPlayerIndex = 0;
            this.nextTurn();
        }else {
            this.currentPlayerIndex = this.currentPlayerIndex +1;
        }
        return this.players.get(this.currentPlayerIndex);
    }


    private void checkClaustrophobiaCards(){
        Player checkPlayer = players.get(currentPlayerIndex);
        List<Integer> toRemove = new ArrayList<>();
        HashMap<Integer, BasicCard> positionCards = checkPlayer.getPositionKart();
        for (Map.Entry<Integer, BasicCard> positionCard : positionCards.entrySet()){
            if (positionCard.getValue() instanceof ClaustrophobiaBasicCard){
                ClaustrophobiaBasicCard c = (ClaustrophobiaBasicCard) positionCard.getValue();
                if (c.CheckIfHeIsCornered2(positionCards, 10, 8, positionCard.getKey())){
                    toRemove.add(positionCard.getKey());
                }
            }
        }
        for (Integer position : toRemove){
            checkPlayer.reactionToAttack(position);
        }
    }

    private void checkStarvelingCard(){
        Player checkPlayer = players.get(currentPlayerIndex);
        List<Integer> toRemove = new ArrayList<>();
        HashMap<Integer, BasicCard> positionCards = checkPlayer.getPositionKart();
        HashMap<Integer, BasicCard> copyPositionCards = new HashMap<>(positionCards);
        for (Map.Entry<Integer, BasicCard> positionCard : copyPositionCards.entrySet()){
            BasicCard stravelingCard = positionCard.getValue();
            if (stravelingCard instanceof Starveling){
                List<Integer> attackPosition = ((Starveling) stravelingCard).getDestroyPosition(positionCard.getKey(), 10);
                for (Integer position : attackPosition){
                    if (positionCards.containsKey(position) && positionCards.get(position).isWeakling()){
                        checkPlayer.reactionToAttack(position);
                    }
                }
            }
        }
    }


    public void checkAfterMovePlayer(){
        checkClaustrophobiaCards();
        checkStarvelingCard();
    }
}
