package com.example.projetlego;

import javafx.scene.image.Image;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;;

public class ImageViewer {


    public Image image;
    public String url;
    public ImageViewer(String url) throws FileNotFoundException {
        String path = "C:\\Users\\François Boulard\\IdeaProjects\\ProjetLEGO\\src\\main\\resources\\"+url;
        InputStream is = new BufferedInputStream(new FileInputStream(path));
        System.out.println(path+" "+new File(path).exists());
        image = new Image(is);
    }
    public Image getImage() {
        return image;
    }
    public Image setImage(String url){
        InputStream input = this.getClass().getResourceAsStream(url);
        image = new Image(input);
        return image;
    }
}
