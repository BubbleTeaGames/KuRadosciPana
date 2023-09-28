package com.example.ukladajzwyciezaj;

import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;

public class Card {
    private HashMap<SideAttack, CardInfuence> ValueAttack;
    private ImageView imageView;
    int ImageResource;
    private int puntaction;

    public Card(CardInfuence left, CardInfuence right, CardInfuence top, CardInfuence bottom, ImageView imageView, int imageResource, int puntaction){
        this.ValueAttack = new HashMap<>();
        this.ImageResource = imageResource;
        this.ValueAttack.put(SideAttack.RIGHT, right);
        this.ValueAttack.put(SideAttack.LEFT, left);
        this.ValueAttack.put(SideAttack.TOP, top);
        this.ValueAttack.put(SideAttack.BOTTOM, bottom);
        this.imageView = imageView;
        this.puntaction = puntaction;
    }

    public Card setOnClickListener(View.OnClickListener listener) {
        imageView.setOnClickListener(listener);
        return this;
    }

    public HashMap<SideAttack, CardInfuence> getValueAttack() {
        return ValueAttack;
    }

    public void setValueAttack(HashMap<SideAttack, CardInfuence> valueAttack) {
        ValueAttack = valueAttack;
    }

    public int getImageResource() {
        return ImageResource;
    }

    public void setImageResource(int imageResource) {
        ImageResource = imageResource;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
