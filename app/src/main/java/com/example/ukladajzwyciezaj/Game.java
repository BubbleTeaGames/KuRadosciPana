package com.example.ukladajzwyciezaj;

import android.content.Context;
import android.widget.GridView;

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
    public Game(Context context, GameActivity gameActivity, ArrayList<String> playerNames) throws IOException {
        this.pileOfCards = new PileOfCards(context, gameActivity);
        this.players = new ArrayList<>();

        for (String playerName : playerNames) {
            try {
                Player player = new Player(context, playerName, this, 8, 10);   //!!! wartosci nie dynamiczne
                this.players.add(player);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        this.currentPlayerIndex = 0;
        this.context = context;
        this.turn = new Turn(this.players, 2);
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
                        toBeRemoved.getFirst().deleteKart(toBeRemoved.getSecond());
                        toBeRemoved.getFirst().getImageAdapter().changeFirstImage(R.drawable.grafika_karty, toBeRemoved.getSecond());
                    }
                }
            }
        }
        return cardsToBeRemoved;
    }


}
