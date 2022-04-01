package fr.antoromeochrist.projetlego.utils.images;

import javafx.scene.image.Image;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ImagePath extends Image{

    private String url;
    public ImagePath(String path) throws FileNotFoundException {
        super(new BufferedInputStream(new FileInputStream("src/main/resources/"+path)));
    }
    public String getPath(){
        return url;
    }
}
