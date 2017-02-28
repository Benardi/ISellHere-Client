package com.example.grupoes.projetoes.models;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Wesley on 27/02/2017.
 */

public class PointOfSale {
    private String creator;
    private String name;
    private String comment;
    private Bitmap image;
    private LatLng position;

    public PointOfSale(String creator, String name, String comment, Bitmap image, LatLng position) {
        this.creator = creator;
        this.name = name;
        this.comment = comment;
        this.image = image;
        this.position = position;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }
}
