package com.example.projetlego;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class ImageStorage {

    private String text;
    private ImageURL image;

    public ImageStorage(String text, ImageURL image){
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public ImageURL getImage() {
        return image;
    }

    public void setImage(ImageURL image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static ArrayList<String> getTexts(ArrayList<ImageStorage> ars){
        ArrayList<String> ar = new ArrayList<>();
        for(ImageStorage iv : ars){
            ar.add(iv.getText());
        }
        return ar;
    }

    public static Image getImage(ArrayList<ImageStorage> list, String text){
        for(ImageStorage il : list){
            if(il.getText().equals(text)){
                return il.getImage();
            }
        }
        return null;
    }


}
