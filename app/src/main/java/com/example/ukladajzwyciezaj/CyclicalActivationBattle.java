package com.example.ukladajzwyciezaj;

import android.content.Context;
import android.content.Intent;

import com.example.ukladajzwyciezaj.CardMechanik.Battle;

import java.util.Random;

public class CyclicalActivationBattle {
    private Context context;
    private Battle battle;
    public CyclicalActivationBattle(Context context, Battle battle){
        this.context = context;
        this.battle = battle;
    }

    private boolean getDecisionAboutBattle(double probability){
        Random random = new Random();
        int range = (int) (1/probability);
        int randomNumber = random.nextInt(range);
        if(randomNumber == 0){
            return true;
        }else {
            return false;
        }
    }

    public void CheckBattle(){
        if (getDecisionAboutBattle(0.1)){
            battle.battle();
            Intent battleImage = new Intent(context, BattleActivity.class);
            context.startActivity(battleImage);
        }
    }
}
