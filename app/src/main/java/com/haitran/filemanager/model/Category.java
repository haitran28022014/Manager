package com.haitran.filemanager.model;

/**
 * Created by Hai Tran on 1/16/2017.
 */

public class Category {
    private String colorBackground;
    private String imageIcon;
    private String nameItem;
    private int numberFileHome;

    public Category(String colorBackground, String imageIcon, String nameItem, int numberFileHome) {
        this.colorBackground = colorBackground;
        this.imageIcon = imageIcon;
        this.nameItem = nameItem;
        this.numberFileHome = numberFileHome;
    }

    public String getColorBackground() {
        return colorBackground;
    }

    public String getImageIcon() {
        return imageIcon;
    }

    public String getNameItem() {
        return nameItem;
    }

    public int getNumberFileHome() {
        return numberFileHome;
    }
}
