package com.example.ukladajzwyciezaj;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ukladajzwyciezaj.Activites.GameActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class PileOfCards {
    private ArrayList<Card> setAllCard;
    private Context context;
    private GameActivity gameActivity;

    public PileOfCards(Context context, GameActivity gameActivity) throws IOException {

        this.context = context;
        this.setAllCard = new ArrayList<>();

        InputStream inputStream = context.getResources().openRawResource(R.raw.set_information_about_kart);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        StringBuilder stringBuilder = new StringBuilder();


        while ((line = bufferedReader.readLine()) != null) {
            ImageView KartView = new ImageView(context);
            String[] actualValue = line.split(";");
            stringBuilder.append(line);
            if(actualValue[0].equals("------name-----")){
                continue;
            }

            int resId = this.context.getResources().getIdentifier(actualValue[0], "drawable", context.getPackageName());
            KartView.setImageResource(resId);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params1.gravity = Gravity.CENTER;
            params1.setMargins(15, 15, 15, 15);
            KartView.setLayoutParams(params1);

            KartView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            Card card = new Card(CardInfuence.valueOf(actualValue[3]), CardInfuence.valueOf(actualValue[1]),
                    CardInfuence.valueOf(actualValue[2]), CardInfuence.valueOf(actualValue[4]), KartView, resId);

            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"Wybrano kartę do wstawienia", Toast.LENGTH_SHORT).show();
                    gameActivity.setChosenKart(card);
                }
            });

            this.setAllCard.add(card);
        }

        // Zamknięcie strumieni
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();
    }

    public Card getRandomKartToGame(){
        if (!this.setAllCard.isEmpty()) {
            int howManyRandom = setAllCard.size();
            Random rand = new Random();
            int randint = rand.nextInt(howManyRandom) + 1;

            Card removeCard = this.setAllCard.remove(randint - 1);
            return removeCard;
        }
        return null;
    }

    public boolean isEmpty(){
        return this.setAllCard.isEmpty();
    }

    public int size(){
        return this.setAllCard.size();
    }

}
