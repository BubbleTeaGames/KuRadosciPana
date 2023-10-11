package com.example.ukladajzwyciezaj;

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

public class Player {
    private ForwardingAttack informationAttack;
    private HashMap<Integer, Card> positionKart;
    private ImageView[] placeToKartImageVIew = new ImageView[80];
    private Context context;
    private ImageAdapter imageAdapter;
    private String Name;
    private ArrayList<Card> cardInHeand;
    private Paws paws;


    public Player(Context context, String name, Game game,int numRow, int numCol) throws IOException {
        this.positionKart = new HashMap<>();
        this.informationAttack = new ForwardingAttack();
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

    public HashMap<Integer, Card> getPositionKart() {
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

    public int getPositionCardAfterGravitation(GridView gridView, int position){
        int numCol = gridView.getNumColumns();
        int numObject = gridView.getAdapter().getCount();
        int positionUnderCard = position + numCol;
        while (positionUnderCard < numObject-1 && !this.positionKart.containsKey(positionUnderCard)) {
            position = positionUnderCard;
            positionUnderCard = position + numCol;
        }
        return position;

    }

    public int EnterCardToPlay(GridView gridView, Card kart, Integer position){
        int numCol = gridView.getNumColumns();
        position = getPositionCardAfterGravitation(gridView, position);
        this.positionKart.put(position, kart);
        this.placeToKartImageVIew[position] = kart.getImageViewToBoard();
        HashMap<SideAttack, CardInfuence> attackKart = kart.getValueAttack();
        this.informationAttack.SaveAttack(attackKart.get(SideAttack.RIGHT),position+1, SideAttack.RIGHT);
        this.informationAttack.SaveAttack(attackKart.get(SideAttack.LEFT),position-1, SideAttack.LEFT);
        this.informationAttack.SaveAttack(attackKart.get(SideAttack.TOP),position - numCol, SideAttack.TOP);
        this.informationAttack.SaveAttack(attackKart.get(SideAttack.BOTTOM),position + numCol, SideAttack.BOTTOM);
        return position;
    }

    public void deleteKart(int position){
        GridView gridView1 = ((Activity) context).findViewById(R.id.gridview);
        int numCol = gridView1.getNumColumns();
        Card cardToRemove = this.positionKart.get(position);
        if (cardToRemove != null) {
            Card.functionDelete deleteFunction = cardToRemove.getFunctionDelete();
            deleteFunction.instructionDelete(informationAttack, positionKart, position, numCol, imageAdapter);
        }
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
                placeToKartImageVIew[position].setImageResource(newImageResource);
                notifyDataSetChanged();
            }
        }
        public void refreshAdapter(){
            notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            /*
            ImageView imageView;

            imageView = placeToKartImageVIew[position];
            imageView.setLayoutParams(new GridView.LayoutParams(500, 500));

            int desiredWidth = 200; // Dostosuj tę wartość do preferencji
            int desiredHeight = 350; // Dostosuj tę wartość do preferencji
            imageView.setLayoutParams(new GridView.LayoutParams(desiredWidth, desiredHeight));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(16,16,16,16);

            //imageView.setImageDrawable(placeToKartImageVIew[position].getDrawable());
            return imageView;

             */

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


