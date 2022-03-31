package com.example.projetlego;

import javafx.scene.image.Image;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ImageURL extends Image{

    public String url;
    public ImageURL(String url) throws FileNotFoundException {
        super(new BufferedInputStream(new FileInputStream("\"src/main/resources/\"+url")));
    }
}
