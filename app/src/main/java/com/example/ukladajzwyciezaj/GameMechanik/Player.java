package com.example.ukladajzwyciezaj.GameMechanik;

import android.app.Activity;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ukladajzwyciezaj.Activites.GameActivity;
import com.example.ukladajzwyciezaj.Card;
import com.example.ukladajzwyciezaj.CardMechanik.AttacksCard;
import com.example.ukladajzwyciezaj.CardMechanik.BasicCard;
import com.example.ukladajzwyciezaj.CardMechanik.ForwardingAttack;
import com.example.ukladajzwyciezaj.Enum.SideAttack;
import com.example.ukladajzwyciezaj.R;

public class Player {
    private ForwardingAttack informationAttack;
    private HashMap<Integer, BasicCard> positionKart;
    private ImageView[] placeToKartImageVIew = new ImageView[80];
    private Context context;
    private ImageAdapter imageAdapter;
    private String Name;
    private ArrayList<Card> cardInHeand;
    private Paws paws;
    GridView cardContainers;
    int numCol;


    public Player(Context context, String name, Game game, int numRow, int numCol, GameActivity gameActivity) throws IOException {
        this.positionKart = new HashMap<>();
        this.informationAttack = new ForwardingAttack(numCol);
        this.context = context;
        this.Name = name;
        this.imageAdapter = new ImageAdapter();
        this.cardInHeand = new ArrayList<>();
        for (int j=0; j<placeToKartImageVIew.length; j++){
            ImageView imageViewToInsert = new ImageView(context);
            int res = this.context.getResources().getIdentifier("grafika_karty","drawable",this.context.getPackageName());
            imageViewToInsert.setImageResource(res);
            this.placeToKartImageVIew[j] = imageViewToInsert;
        }
        this.completeCartInHeand(game);
        this.paws = new Paws(context, numRow, numCol);
        this.cardContainers = gameActivity.getCardContainers();
        this.numCol = numCol;
    }

    public int getNumCol() {
        return numCol;
    }

    public ArrayList<Card> getCardInHeand() {
        return cardInHeand;
    }

    public Context getContext() {
        return context;
    }

    public Paws getPaws() {
        return paws;
    }

    public HashMap<Integer, BasicCard> getPositionKart() {
        return positionKart;
    }

    public String getName() {
        return Name;
    }

    public ImageAdapter getImageAdapter() {
        return imageAdapter;
    }

    public ForwardingAttack getInformationAttack() {
        return informationAttack;
    }
    public void completeCartInHeand(Game game){
        //LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        int Childcount = this.cardInHeand.size();
        if (Childcount < 2 ) {
            while (Childcount < 3) {
                //if (game.getPileOfKart().size() < 1){
                //    Toast.makeText(context,"Koniec kart w tali", Toast.LENGTH_SHORT).show();
                //    break;
                //}
                Card card = game.getRandomCard();
                this.cardInHeand.add(card);
                Childcount = this.cardInHeand.size();
            }
        }else{
            Toast.makeText(context,"Nie możesz uzupełnić kart", Toast.LENGTH_SHORT).show();
        }
    }

    public void SetViewLinearlayout(LinearLayout linearLayout){
        linearLayout.removeAllViews();
        for(int i = 0; i<this.cardInHeand.size(); i++){
            linearLayout.addView(this.cardInHeand.get(i).getImageViewToCardsInHand());
        }
    }

    public int EnterCardToPlay(BasicCard kart, Integer position){
        int numCol = cardContainers.getNumColumns();
        this.positionKart.put(position, kart);
        this.placeToKartImageVIew[position] = kart.getImageViewToBoard();
        AttacksCard attacksKart = kart.getAttacksCard();
        this.informationAttack.SaveAttackFrom(attacksKart.getForSide(SideAttack.RIGHT), position, SideAttack.RIGHT);
        this.informationAttack.SaveAttackFrom(attacksKart.getForSide(SideAttack.LEFT), position, SideAttack.LEFT);
        this.informationAttack.SaveAttackFrom(attacksKart.getForSide(SideAttack.TOP), position, SideAttack.TOP);
        this.informationAttack.SaveAttackFrom(attacksKart.getForSide(SideAttack.BOTTOM), position, SideAttack.BOTTOM);
        return position;
    }

    public void reactionToAttack(int position){
        GridView gridView1 = ((Activity) context).findViewById(R.id.gridview);
        int numCol = gridView1.getNumColumns();
        BasicCard cardToRemove = this.positionKart.get(position);
        if (cardToRemove != null) {
            BasicCard.functionDelete deleteFunction = cardToRemove.getFunctionDelete();
            deleteFunction.instructionDelete(informationAttack, positionKart, position, numCol, imageAdapter);
        }
    }

    public void completeRemove(int position){
        informationAttack.RemoveAttack(position, SideAttack.LEFT);
        informationAttack.RemoveAttack(position, SideAttack.RIGHT);
        informationAttack.RemoveAttack(position, SideAttack.BOTTOM);
        informationAttack.RemoveAttack(position, SideAttack.TOP);
        positionKart.remove(position);
        imageAdapter.changeFirstImage(R.drawable.grafika_karty, position);
    }

    public class ImageAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return placeToKartImageVIew.length;
        }

        @Override
        public Object getItem(int position) {
            return placeToKartImageVIew[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void changeFirstImage(int newImageResource, int position) {
            if (placeToKartImageVIew.length > position) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(newImageResource);
                placeToKartImageVIew[position] = imageView;
                notifyDataSetChanged();
            }
        }
        public void refreshAdapter(){
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view ;
            //ImageView imageView = placeToKartImageVIew[position];
            //if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(context);
                view = inflater.inflate(R.layout.players_pawn, null);
            //}

            // Pobierz ImageView i ustaw ikonkę
            ImageView imageView1 = (ImageView) view.findViewById(R.id.imageView);
            imageView1.setImageDrawable(placeToKartImageVIew[position].getDrawable());

            // Pobierz drugi ImageView (ikonkę) i ustaw ikonkę na środku
            ImageView iconImageView = view.findViewById(R.id.iconImageView);
            iconImageView.setImageResource(R.drawable.ic_launcher_foreground);
            if (getPaws().getActualPositoin() != null && getPaws().getActualPositoin() == position){
                iconImageView.setVisibility(View.VISIBLE);
            }
            return view;
        }
    }
}

