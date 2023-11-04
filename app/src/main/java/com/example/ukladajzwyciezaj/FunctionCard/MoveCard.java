package com.example.ukladajzwyciezaj.FunctionCard;

import android.content.Context;
import android.widget.ImageView;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.CardMechanik.BasicCard;
import com.example.ukladajzwyciezaj.GameMechanik.Player;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

public class MoveCard extends FunctionCardExchange{
    List<Integer> correctField;
    public MoveCard(Context context, GameActivity gameActivity, ImageView mainView) {
        super(context, gameActivity, mainView);
    }
    public boolean firstSkillAction(int position1, Player player){
        if (firstCardSelected == null && player.getPositionKart().containsKey(position1)){
            setFirstCardSelected(new Pair<>(player.getPositionKart().get(position1), position1));
            this.correctField = getCorrectFields(position1, player.getNumCol());
        } else if (!player.getPositionKart().containsKey(position1) && correctField.contains(position1)){
            moveCard(position1, player);
            return true;
        }
        return false;
    }
    private void moveCard(int position1, Player player){
        if (correctField.contains(position1)){
            player.EnterCardToPlay(firstCardSelected.getFirst(), position1);
            player.completeRemove(firstCardSelected.getSecond());
            player.getImageAdapter().refreshAdapter();
            firstCardSelected = null;
        }
    }

    public List<Integer> getCorrectFields(int position, int numCol){
        List<Integer> correctFields = new ArrayList<>();
        correctFields.add(position - numCol);
        correctFields.add(position - numCol -1);
        correctFields.add(position - numCol +1);
        return correctFields;
    }
}
