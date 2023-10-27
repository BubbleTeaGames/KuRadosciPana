package com.example.ukladajzwyciezaj;

import android.widget.GridView;

import com.example.ukladajzwyciezaj.CardChildren.Fatty;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import java.util.Comparator;

public class Gravitation {

    private GridView cardContainers;

    public Gravitation(GridView cardContainers){
        this.cardContainers = cardContainers;
    }

    public int getPositionCardAfterGravitation(HashMap<Integer, Card> positionCard, int position){
        int numCol = cardContainers.getNumColumns();
        int numObject = cardContainers.getAdapter().getCount();
        int positionUnderCard = position + numCol;
        while (positionUnderCard < numObject-1 && !positionCard.containsKey(positionUnderCard)) {
            position = positionUnderCard;
            positionUnderCard = position + numCol;
        }
        return position;
    }

    public void gravitationWeakling(Player player){
        HashMap<Integer, Card> positionCard = player.getPositionKart();
        HashMap<Integer, Card> copyPositionCard = new HashMap<>(positionCard);
        Stream<Map.Entry<Integer, Card>> sortedCopyPositionCard = copyPositionCard.entrySet().stream()
                .sorted(Map.Entry.<Integer, Card>comparingByKey(Comparator.reverseOrder()));
        sortedCopyPositionCard.forEach(position_card ->{
            int positionAfterGravitation = getPositionCardAfterGravitation(player.getPositionKart(), position_card.getKey());
            if (positionAfterGravitation != position_card.getKey() && position_card.getValue().isWeakling()){
                player.completeRemove(position_card.getKey());
            } else if (positionAfterGravitation != position_card.getKey()){
                if (position_card.getValue() instanceof Fatty){
                    player.completeRemove(positionAfterGravitation + player.getNumCol());
                    positionAfterGravitation = getPositionCardAfterGravitation(player.getPositionKart(), position_card.getKey());
                }
                player.completeRemove(position_card.getKey());
                player.EnterCardToPlay(position_card.getValue(), positionAfterGravitation);
                player.getImageAdapter().notifyDataSetChanged();
            }
        } );
    }

    public void gravitationFatty(Player player){
        HashMap<Integer, Card> positionCard = player.getPositionKart();
        HashMap<Integer, Card> copyPositionCard = new HashMap<>(positionCard);
        Stream<Map.Entry<Integer, Card>> sortedCopyPositionCard = copyPositionCard.entrySet().stream()
                .sorted(Map.Entry.<Integer, Card>comparingByKey(Comparator.reverseOrder()));
        sortedCopyPositionCard.forEach(position_card ->{
            int positionAfterGravitation = getPositionCardAfterGravitation(player.getPositionKart(), position_card.getKey());
            if (position_card.getValue() instanceof Fatty && positionAfterGravitation != position_card.getKey()
                    && positionCard.containsKey(position_card.getKey()-player.getNumCol())){
                player.EnterCardToPlay(position_card.getValue(), position_card.getKey() - player.getNumCol());
            }
        });
    }
}
