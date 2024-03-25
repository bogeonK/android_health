package com.example.myapplication3;

public class AbsModel {
    String title;
    String image_path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public AbsModel(String title, String image_path) {
        this.title = title;
        this.image_path = image_path;
    }

}
