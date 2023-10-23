package com.example.ukladajzwyciezaj;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.CardChildren.ClaustrophobiaCard;
import com.example.ukladajzwyciezaj.CardChildren.CoveredCard;
import com.example.ukladajzwyciezaj.CardChildren.Fatty;
import com.example.ukladajzwyciezaj.CardChildren.KillerCard;
import com.example.ukladajzwyciezaj.CardChildren.RotatingCard;

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
        this.gameActivity = gameActivity;

        InputStream inputStream = context.getResources().openRawResource(R.raw.set_information_about_kart);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        StringBuilder stringBuilder = new StringBuilder();


        while ((line = bufferedReader.readLine()) != null) {
            //ImageView KartView = new ImageView(context);
            String[] actualValue = line.split(";");
            stringBuilder.append(line);
            if(actualValue[0].equals("---name---")){
                continue;
            }

            Card card = makeCard(actualValue);

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

    public Card makeCard(String[] informationAboutCard) {
        ImageView KartView = new ImageView(context);
        int resId = this.context.getResources().getIdentifier(informationAboutCard[0],
                "drawable", context.getPackageName());
        KartView.setImageResource(resId);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        params1.gravity = Gravity.CENTER;
        params1.setMargins(15, 15, 15, 15);
        KartView.setLayoutParams(params1);

        KartView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        boolean weakling = !informationAboutCard[7].equals("false");

        if (informationAboutCard[6].equals("rotating")) {
            int resIdArterRotatin = this.context.getResources().getIdentifier(informationAboutCard[8],
                    "drawable", context.getPackageName());
            Card card = new RotatingCard(CardInfuence.valueOf(informationAboutCard[3]),
                    CardInfuence.valueOf(informationAboutCard[1]),
                    CardInfuence.valueOf(informationAboutCard[2]), CardInfuence.valueOf(informationAboutCard[4]),
                    KartView, resId, Integer.valueOf(informationAboutCard[5]), resIdArterRotatin, gameActivity, context, weakling);
            return card;
        } else if (informationAboutCard[6].equals("ninja")) {
            int resIdCurtain = this.context.getResources().getIdentifier(informationAboutCard[8],
                    "drawable", context.getPackageName());
            Card card = new CoveredCard(CardInfuence.valueOf(informationAboutCard[3]),
                    CardInfuence.valueOf(informationAboutCard[1]),
                    CardInfuence.valueOf(informationAboutCard[2]), CardInfuence.valueOf(informationAboutCard[4]),
                    KartView, resId, Integer.valueOf(informationAboutCard[5]), resIdCurtain, gameActivity, context, weakling);
            return card;
        } else if (informationAboutCard[6].equals("killer")) {
            Card card = new KillerCard(CardInfuence.valueOf(informationAboutCard[3]),
                    CardInfuence.valueOf(informationAboutCard[1]), CardInfuence.valueOf(informationAboutCard[2]),
                    CardInfuence.valueOf(informationAboutCard[4]), KartView, resId,
                    Integer.valueOf(informationAboutCard[5]), gameActivity, context, weakling);
            return card;
        } else if (informationAboutCard[6].equals("claustrophobia")) {
            Card card = new ClaustrophobiaCard(CardInfuence.valueOf(informationAboutCard[3]),
                    CardInfuence.valueOf(informationAboutCard[1]), CardInfuence.valueOf(informationAboutCard[2]),
                    CardInfuence.valueOf(informationAboutCard[4]), KartView, resId,
                    Integer.valueOf(informationAboutCard[5]), gameActivity, context, weakling);
            return card;
        } else if (informationAboutCard[6].equals("fatty")) {
            Card card = new Fatty(CardInfuence.valueOf(informationAboutCard[3]),
                    CardInfuence.valueOf(informationAboutCard[1]), CardInfuence.valueOf(informationAboutCard[2]),
                    CardInfuence.valueOf(informationAboutCard[4]), KartView, resId,
                    Integer.valueOf(informationAboutCard[5]), gameActivity, context, weakling);
            return card;
        }else {
            Card card = new Card(CardInfuence.valueOf(informationAboutCard[3]),
                    CardInfuence.valueOf(informationAboutCard[1]), CardInfuence.valueOf(informationAboutCard[2]),
                    CardInfuence.valueOf(informationAboutCard[4]), KartView, resId,
                    Integer.valueOf(informationAboutCard[5]), gameActivity, context, weakling);
            return card;
        }
    }

    public boolean isEmpty(){
        return this.setAllCard.isEmpty();
    }

    public int size(){
        return this.setAllCard.size();
    }

}
