package com.example.gpcorser.myapplication;

/**
 * Created by gpcorser on 9/26/2016.
 */
public class Drink {
    private String name;
    private String description;
    private int imageResourceId;

    // drinks is an array of drinks
    public static final Drink[] drinks = {
            new Drink ("Latte", "A couple of espresso shots with steamed milk", R.drawable.latte),
            new Drink ("Cappuccino", "Espresso, hot milk and steamed milk foam", R.drawable.cappuccino),
            new Drink ("Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter)
    };
    //each drink has a name, description and an image resource
    private Drink (String name, String description, int imageResourceId) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    public int getImageResourceId () {
        return imageResourceId;
    }
    public String toString() {
        return this.name;
    }
}
