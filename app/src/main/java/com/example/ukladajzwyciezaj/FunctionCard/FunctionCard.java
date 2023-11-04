package com.example.ukladajzwyciezaj.FunctionCard;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.Card;
import com.example.ukladajzwyciezaj.CardMechanik.BasicCard;
import com.example.ukladajzwyciezaj.Enum.CardInfuence;
import com.example.ukladajzwyciezaj.Enum.SideAttack;
import com.example.ukladajzwyciezaj.GameMechanik.Player;

import java.util.Arrays;
import java.util.List;

public abstract class FunctionCard implements Card {
    private Context context;
    private GameActivity gameActivity;
    private ImageView mainView;
    protected boolean isSelectedFirstSkill;
    public FunctionCard(Context context, GameActivity gameActivity, ImageView mainView){
        this.context = context;
        this.gameActivity = gameActivity;
        this.mainView = mainView;
        this.setOnClick();
    }

    public GameActivity getGameActivity() {
        return gameActivity;
    }

    @Override
    public void setOnClick(){
        setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameActivity.getGame().getTurn().checkPossiblityMovement(gameActivity.getCurrentPlayer())) {
                    Toast.makeText(context, "!!!", Toast.LENGTH_SHORT).show();
                    showYesNoDialog(view);
                }
            }
        });
    }

    @Override
    public FunctionCard setOnClickListener(View.OnClickListener listener) {
        mainView.setOnClickListener(listener);
        return this;
    }

    @Override
    public ImageView getImageViewToCardsInHand(){
        return this.mainView;
    }

    public void showYesNoDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Wybierz umiejętność:")
                .setPositiveButton("Pierwsza", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        gameActivity.setFunctionCard(FunctionCard.this);
                        isSelectedFirstSkill = true;
                    }
                })
                .setNegativeButton("Druga", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        gameActivity.setFunctionCard(FunctionCard.this);
                        isSelectedFirstSkill = false;
                    }
                })
                .setNeutralButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        gameActivity.setFunctionCard(null);
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean actionFunctionCard(int position1, Player player) {
        if (isSelectedFirstSkill){
            return firstSkillAction(position1, player);
        }else {
            return secondSkillAction(position1, player);
        }
    }

    protected boolean secondSkillAction(int position1, Player player){
        List<SideAttack> sideAttacksToEnum = Arrays.asList(SideAttack.RIGHT, SideAttack.LEFT, SideAttack.TOP, SideAttack.BOTTOM);
        List<CardInfuence> attackInfuences = Arrays.asList(CardInfuence.TRIPLE_ATTACK, CardInfuence.DOUBLE_ATTACK, CardInfuence.ATTACK);
        BasicCard forcedAttack = player.getPositionKart().get(position1);
        for(SideAttack side: sideAttacksToEnum) {
            int attackedPosition = getAttackPosition(side, position1);
            BasicCard attackedCard = player.getPositionKart().get(attackedPosition);
            if(attackInfuences.contains(forcedAttack.getAttacksCard().getForSide(side)) && attackedCard.getAttacksCard().getForSide(getOppositeSide(side)) != CardInfuence.DEFENSE){
                player.reactionToAttack(attackedPosition);
            }
        }
        return true;
    }

    public int getAttackPosition(SideAttack side, int position){
        Integer positionReceivingAttack;
        int numCol = getGameActivity().getCurrentPlayer().getNumCol();
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
    abstract boolean firstSkillAction(int position1, Player player);
}
