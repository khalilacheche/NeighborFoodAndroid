package com.epfl.neighborfood.neighborfoodandroid.models;


public class Meal {
    private String name, shortDescription, longDescription;
    private int imageId;
    // TODO add other attributes


    public Meal(String name, String shortDescription, String longDescription, int imageId) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public int getImageId() {
        return imageId;
    }
}