package com.example.ukladajzwyciezaj;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import com.example.ukladajzwyciezaj.SideAttack;
import java.util.HashMap;

public class Kart {
    private HashMap<SideAttack, InfluenceKart> ValueAttack;
    private ImageView imageView;
    int ImageResource;
    private int puntaction;

    public Kart(InfluenceKart left, InfluenceKart right, InfluenceKart top, InfluenceKart bottom, ImageView imageView, int imageResource, int puntaction){
        this.ValueAttack = new HashMap<>();
        this.ImageResource = imageResource;
        this.ValueAttack.put(SideAttack.RIGHT, right);
        this.ValueAttack.put(SideAttack.LEFT, left);
        this.ValueAttack.put(SideAttack.TOP, top);
        this.ValueAttack.put(SideAttack.BOTTOM, bottom);
        this.imageView = imageView;
        this.puntaction = puntaction;
    }

    public Kart setOnClickListener(View.OnClickListener listener) {
        imageView.setOnClickListener(listener);
        return this;
    }

    public HashMap<SideAttack, InfluenceKart> getValueAttack() {
        return ValueAttack;
    }

    public void setValueAttack(HashMap<SideAttack, InfluenceKart> valueAttack) {
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
