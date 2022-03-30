package com.example.projetlego;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ImageLinked {

    private String text;
    private ImageViewer image;

    public ImageLinked(String text,ImageViewer image){
        this.text = text;
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public ImageViewer getImage() {
        return image;
    }

    public void setImage(ImageViewer image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static ArrayList<String> getTexts(ArrayList<ImageLinked> ars){
        ArrayList<String> ar = new ArrayList<>();
        for(ImageLinked iv : ars){
            ar.add(iv.getText());
        }
        return ar;
    }

    public static Image getImage(ArrayList<ImageLinked> list,String text){
        for(ImageLinked il : list){
            if(il.getText().equals(text)){
                return il.getImage().getImage();
            }
        }
        return null;
    }


}
