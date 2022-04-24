package fr.antoromeochrist.projetlego.utils.images;

import javafx.scene.image.Image;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class ImagePath extends Image{

    private String path;
    public ImagePath(String path) throws FileNotFoundException {
        super(new BufferedInputStream(new FileInputStream("src/main/resources/"+path)));
        this.path=path;
    }
    public String getPath(){
        return path;
    }

}
