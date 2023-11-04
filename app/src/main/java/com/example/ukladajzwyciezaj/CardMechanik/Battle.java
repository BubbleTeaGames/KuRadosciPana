package com.example.ukladajzwyciezaj.CardMechanik;

import com.example.ukladajzwyciezaj.CardChildren.Kamikaze;
import com.example.ukladajzwyciezaj.Enum.CardInfuence;
import com.example.ukladajzwyciezaj.Enum.SideAttack;
import com.example.ukladajzwyciezaj.GameMechanik.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import kotlin.Pair;

public class Battle {
    private ArrayList<Player> players;
    public Battle(ArrayList<Player> players){
        this.players = players;
    }

    private static List<CardInfuence> attackInfuences = Arrays.asList(CardInfuence.TRIPLE_ATTACK, CardInfuence.DOUBLE_ATTACK, CardInfuence.ATTACK);
    private static List<SideAttack> sideAttacksToEnum = Arrays.asList(SideAttack.RIGHT, SideAttack.LEFT, SideAttack.TOP, SideAttack.BOTTOM);
    public void battle(){
        for (Player player : this.players) {
            for (CardInfuence powerAttack : attackInfuences){       //strony ataku
                ArrayList<Pair<Player, Integer>> cardsToBeRemoved = new ArrayList<>();
                for (SideAttack actualAttack : sideAttacksToEnum){
                    getCardToBeRemoved(cardsToBeRemoved, player, actualAttack, powerAttack);
                }
                deleteAttackedCard(cardsToBeRemoved);
            }
        }
    }

    private void deleteAttackedCard(ArrayList<Pair<Player, Integer>> cardsToBeRemoved){
        for (Pair<Player, Integer> toBeRemoved : cardsToBeRemoved){
            toBeRemoved.getFirst().reactionToAttack(toBeRemoved.getSecond());
        }
    }

    private void getCardToBeRemoved(ArrayList<Pair<Player, Integer>> cardsToBeRemoved, Player player, SideAttack actualSideAttack, CardInfuence powerAttack){
        CardInfuence[] sideAttack = player.getInformationAttack().getSetSideAttack().getForSide(actualSideAttack);
        List<Integer> KamikazePosition = getPositionKamikazeCard(player);
        for (int index = 0; index<sideAttack.length; index++) {
            if (!player.getPositionKart().containsKey(index)){
                continue;
            }

            BasicCard attackCard = player.getPositionKart().get(index);
            if ((sideAttack[index] == powerAttack) && (attackCard.getAttacksCard().getForSide(actualSideAttack) != CardInfuence.DEFENSE) ){
                deleteKamikazeCardIfAttack(cardsToBeRemoved, index, actualSideAttack, player);
                cardsToBeRemoved.add(new Pair<>(player, index));
            }
        }
    }

    private void deleteKamikazeCardIfAttack(ArrayList<Pair<Player, Integer>> cardsToBeRemoved, int position, SideAttack side, Player player){
        ForwardingAttack forwardingAttack = player.getInformationAttack();
        int attackDirection = forwardingAttack.getAttackPosition(side, position);
        if (player.getPositionKart().get(attackDirection) instanceof Kamikaze){
            cardsToBeRemoved.add(new Pair<>(player, attackDirection));
        }
    }

    private static SideAttack getSideToCheckDefense(Integer side) {
        if (side == 0) {
            return SideAttack.LEFT;
        } else if (side == 1) {
            return SideAttack.RIGHT;
        } else if (side == 2) {
            return SideAttack.BOTTOM;
        } else {
            return SideAttack.TOP;
        }
    }

    private List<Integer> getPositionKamikazeCard(Player player){
        List<Integer> positionKamikaze = new ArrayList<>();
        for (Map.Entry<Integer, BasicCard> positionCard : player.getPositionKart().entrySet()){
            if (positionCard.getValue() instanceof Kamikaze){
                positionKamikaze.add(positionCard.getKey());
            }
        }
        return positionKamikaze;
    }

    private Integer givePlaceUnderAttack(SideAttack side, int position, int numCol){
        if (side == SideAttack.TOP){
            return position - numCol;
        } else if (side == SideAttack.RIGHT) {
            return position +1;
        } else if (side == SideAttack.LEFT) {
            return position - 1;
        }else {
            return position + numCol;
        }
    }

}
