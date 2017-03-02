package com.example.grupoes.projetoes.beans;

/**
 * Created by Wesley on 02/03/2017.
 */

public class SearchBean {
    private String name;
    private double latitude;
    private double longitude;
    private int ray;

    public SearchBean(String name, double latitude, double longitude, int ray) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.ray = ray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRay() {
        return ray;
    }

    public void setRay(int ray) {
        this.ray = ray;
    }
}
