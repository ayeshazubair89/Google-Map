package com.example.googlemap;

public class Note {
    private String text;
    private double latitude;
    private double longitude;

    public Note(String text, double latitude, double longitude) {
        this.text = text;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String gettext() {
        return text;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}


