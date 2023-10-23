package com.example.ukladajzwyciezaj;

import android.content.Context;
import android.content.Intent;

import com.example.ukladajzwyciezaj.Activites.FinishActivity;
import com.example.ukladajzwyciezaj.Activites.GameActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.Pair;


public class Game {
    private PileOfCards pileOfCards;
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private Context context;

    private Turn turn;
    private Integer numColCardContainer;
    private Integer numRowCardContainer;
    private GameActivity gameActivity;

    public Game(Context context, GameActivity gameActivity, ArrayList<String> playerNames, Integer numCol, Integer numRow) throws IOException {
        this.pileOfCards = new PileOfCards(context, gameActivity);
        this.players = new ArrayList<>();
        this.numColCardContainer = numCol;
        this.numRowCardContainer = numRow;

        for (String playerName : playerNames) {
            try {
                Player player = new Player(context, playerName, this, numRow, numCol, gameActivity);
                this.players.add(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.currentPlayerIndex = 0;
        this.context = context;
        this.turn = new Turn(this.players, 2, 1, gameActivity.getCardContainers());
        this.gameActivity = gameActivity;
    }

    public PileOfCards getPileOfKart() {
        return pileOfCards;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Turn getTurn() {
        return turn;
    }

    private static List<CardInfuence> attackInfuences = Arrays.asList(CardInfuence.TRIPLE_ATTACK, CardInfuence.DOUBLE_ATTACK, CardInfuence.ATTACK);

    public ArrayList<Pair<Player, Integer>> battle(){
        ArrayList<Pair<Player, Integer>> cardsToBeRemoved = new ArrayList<>();

        for (Player player : this.players) {
            for (int i = 0; i < 4; i++){
                HashMap<Integer, CardInfuence> sideAttack = player.getInformationAttack().getSetSideAttack().get(i);

                for (CardInfuence powerAttack : attackInfuences){
                    for (Map.Entry<Integer, CardInfuence> entry : sideAttack.entrySet()) {

                        if (!player.getPositionKart().containsKey(entry.getKey())){
                            continue;
                        }

                        Card attackCard = player.getPositionKart().get(entry.getKey());
                        SideAttack SideToDefense = helpMetod.getSideToCheckDefense(player.getContext(), i);

                        if ((entry.getValue() == powerAttack) && (attackCard.getValueAttack().get(SideToDefense) != CardInfuence.DEFENSE) ){
                            Pair<Player, Integer> playerFiled = new Pair<>(player, entry.getKey());
                            cardsToBeRemoved.add(playerFiled);
                        }
                    }

                    for (Pair<Player, Integer> toBeRemoved : cardsToBeRemoved){
                        toBeRemoved.getFirst().reactionToAttack(toBeRemoved.getSecond());
                        cardsToBeRemoved.remove(toBeRemoved);
                        //toBeRemoved.getFirst().getImageAdapter().changeFirstImage(R.drawable.grafika_karty, toBeRemoved.getSecond());
                    }
                }
            }
        }
        return cardsToBeRemoved;
    }

    public HashMap<Player, Integer> calculateScores(){
        HashMap<Player, Integer> scores = new HashMap<>();
        for (Player player : players){
            scores.put(player, 0);
        }
        for (Player player : this.players) {
            double multiplierPoint;
            if (player.getPaws().pawsInBoard()) {
                multiplierPoint = player.getPaws().getRowPawn() - 1;
            }else {
                multiplierPoint = 1;
            }
            HashMap<Integer, Card> positionCard = player.getPositionKart();
            for (Map.Entry<Integer, Card> entry : positionCard.entrySet()){
                int rowCard = (int) getRowCard(entry.getKey());
                if (rowCard > multiplierPoint) {
                    rowCard = 1;
                }
                scores.put(player, scores.get(player)+(entry.getValue().getPuntaction()*rowCard));
            }
        }
        return scores;
    }

    public Card getRandomCard(){
        Card randomCard = this.pileOfCards.getRandomKartToGame();
        if (randomCard == null){
            Intent intent = new Intent(gameActivity, FinishActivity.class);
            HashMap<Player, Integer> scores = calculateScores();
            ArrayList<String> finallyRankingPlayer = getFinallyRankingPlayer(scores);
            intent.putStringArrayListExtra("Ranking", finallyRankingPlayer);
            gameActivity.startActivity(intent);
        }
        return randomCard;
    }

    private double getRowCard(int position){
        double rowCard = this.numRowCardContainer-((position)/(this.numColCardContainer));
        return Math.ceil(rowCard);
    }

    public ArrayList<String> getFinallyRankingPlayer(HashMap<Player, Integer> playerPunctation){
        List<Map.Entry<Player, Integer>> list = new ArrayList<>(playerPunctation.entrySet());

        list.sort((entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()));

        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : list){
            result.add(entry.getKey().getName());
        }
        return result;
    }

}
