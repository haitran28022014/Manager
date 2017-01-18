package com.haitran.filemanager.manager;

import com.haitran.filemanager.model.Category;

import java.util.ArrayList;

/**
 * Created by Hai Tran on 1/16/2017.
 */

public class HomeManager {
    private ArrayList<Category> horoscopes;

    public HomeManager() {

    }

    public ArrayList<Category> getCategory() {
        horoscopes = new ArrayList<>();
        horoscopes.add(new Category("#e53e6f", "icon_image", "Images", 39));
        horoscopes.add(new Category("#73077a", "icon_vedio", "Videos", 3));
        horoscopes.add(new Category("#ffaa30", "icon_music", "Music", 47));
        horoscopes.add(new Category("#4e2eb3", "icon_favorite", "Favorites", 1));
        horoscopes.add(new Category("#1071ae", "icon_app", "Apps", 0));
        horoscopes.add(new Category("#99c210", "icon_document", "Documents", 0));
        horoscopes.add(new Category("#01989e", "icon_download", "Downloads", 1));
        horoscopes.add(new Category("#13acdf", "icon_large", "Large files", 0));
        horoscopes.add(new Category("#822950", "icon_game", "Games", 0));
        return horoscopes;
    }


}
