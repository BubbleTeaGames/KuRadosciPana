package com.example.ukladajzwyciezaj;

import android.app.Activity;
import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ukladajzwyciezaj.Kart;
import com.example.ukladajzwyciezaj.PileOfKart;

public class Player {
    private PileOfKart pileOfKart;
    private ForwardingAttack informationAttack;
    protected HashMap<Integer, Kart> positionKart;
    private ImageView[] placeToKartImageVIew = new ImageView[80];
    private Context context;
    private ImageAdapter imageAdapter;
    private String Name;
    private ArrayList<Kart> KartInHeand;


    public Player(Context context, String name, Game game) throws IOException {
        //this.pileOfKart = new PileOfKart(context);
        this.positionKart = new HashMap<>();
        this.informationAttack = new ForwardingAttack();
        this.context = context;
        this.Name = name;
        this.imageAdapter = new ImageAdapter();
        this.KartInHeand = new ArrayList<>();
        for (int j=0; j<placeToKartImageVIew.length; j++){
            ImageView imageViewToInsert = new ImageView(context);
            int res = this.context.getResources().getIdentifier("grafika_karty","drawable",this.context.getPackageName());
            imageViewToInsert.setImageResource(res);
            this.placeToKartImageVIew[j] = imageViewToInsert;
        }
        this.completeCartInHeand(game);
    }

    public Context getContext() {
        return context;
    }

    public PileOfKart getPileOfKart() {
        return pileOfKart;
    }


    public void setPileOfKart(PileOfKart pileOfKart) {
        this.pileOfKart = pileOfKart;
    }
    //public void RemoveKart(GridView gridView, int positionStart, int positionFinish){

    //}

    public HashMap<Integer, Kart> getPositionKart() {
        return positionKart;
    }

    public String getName() {
        return Name;
    }

    public ImageAdapter getImageAdapter() {
        return imageAdapter;
    }

    public void setImageAdapter(ImageAdapter imageAdapter) {
        this.imageAdapter = imageAdapter;
    }

    public ForwardingAttack getInformationAttack() {
        return informationAttack;
    }
    public void completeCartInHeand(Game game){
        //LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        int Childcount = this.KartInHeand.size();
        if (Childcount < 2 ) {
            while (Childcount < 3) {
                if (game.getPileOfKart().size() < 1){
                    Toast.makeText(context,"Koniec kart w tali", Toast.LENGTH_SHORT).show();
                    break;
                }
                Kart kart = game.getPileOfKart().getRandomKartToGame();
                this.KartInHeand.add(kart);
                Childcount = this.KartInHeand.size();
            }
        }else{
            Toast.makeText(context,"Nie możesz uzupełnić kart", Toast.LENGTH_SHORT).show();
        }
    }

    public void SetViewLinearlayout(LinearLayout linearLayout){
        linearLayout.removeAllViews();
        for(int i=0; i<this.KartInHeand.size(); i++){
            linearLayout.addView(this.KartInHeand.get(i).getImageView());
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

    public int EnterCardToPlay(GridView gridView, Kart kart, Integer position){
        int numCol = gridView.getNumColumns();
        position = getPositionCardAfterGravitation(gridView, position);
        this.positionKart.put(position, kart);
        HashMap<SideAttack, InfluenceKart> attackKart = kart.getValueAttack();
        this.informationAttack.SaveAttack(attackKart.get(SideAttack.RIGHT),position+1, SideAttack.RIGHT);
        this.informationAttack.SaveAttack(attackKart.get(SideAttack.LEFT),position-1, SideAttack.LEFT);
        this.informationAttack.SaveAttack(attackKart.get(SideAttack.TOP),position - numCol, SideAttack.TOP);
        this.informationAttack.SaveAttack(attackKart.get(SideAttack.BOTTOM),position + numCol, SideAttack.BOTTOM);
        return position;
    }

    public void DeleteKart(int position){
        this.positionKart.remove(position);
        GridView gridView1 = ((Activity) context).findViewById(R.id.gridview);
        int numCol = gridView1.getNumColumns();
        this.informationAttack.RemoveAttack(position-1, SideAttack.LEFT);
        this.informationAttack.RemoveAttack(position+1, SideAttack.RIGHT);
        this.informationAttack.RemoveAttack(position+numCol, SideAttack.BOTTOM);
        this.informationAttack.RemoveAttack(position-numCol, SideAttack.TOP);
    }



    public class ImageAdapter extends BaseAdapter {
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            //if (convertView == null){
                imageView = placeToKartImageVIew[position];
                imageView.setLayoutParams(new GridView.LayoutParams(500, 500));

                int desiredWidth = 200; // Dostosuj tę wartość do preferencji
                int desiredHeight = 350; // Dostosuj tę wartość do preferencji
                imageView.setLayoutParams(new GridView.LayoutParams(desiredWidth, desiredHeight));
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imageView.setPadding(16,16,16,16);
                //
                //imageView = placeToKartImageVIew[position];
            //}else {
                //imageView = (ImageView) convertView;
            //}
            imageView.setImageDrawable(placeToKartImageVIew[position].getDrawable());
            return imageView;
        }
    }

}


