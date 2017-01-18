package com.haitran.filemanager.model;

/**
 * Created by Hai Tran on 10/2/2016.
 */

public class FileItem {
    private String name;
    private String pathFile;
    private String date;
    private int numberItem;
    private boolean isFile;
    private boolean isDirectory;
    private boolean select;

    public FileItem(String name, String pathFile, String date, int numberItem, boolean isFile, boolean isDirectory, boolean select) {
        this.name = name;
        this.pathFile = pathFile;
        this.date = date;
        this.numberItem = numberItem;
        this.isFile = isFile;
        this.isDirectory = isDirectory;
        this.select = select;
    }

    public String getName() {
        return name;
    }

    public String getPathFile() {
        return pathFile;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getNumberItem() {
        return numberItem;
    }

    public String getDate() {
        return date;
    }

    public boolean isFile() {
        return isFile;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setName(String name) {
        this.name = name;
    }
}
