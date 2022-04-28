package fr.antoromeochrist.projetlego.utils.images;

import javafx.scene.image.Image;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Cette classe permet de simplifier la création
 * <p>
 * d'image avec le chemin de l'image.
 */
public class ImagePath extends Image {

    /**
     * Contructeur
     *
     * @param path le chemin du chier
     */
    public ImagePath(String path) throws FileNotFoundException {
        super(new BufferedInputStream(new FileInputStream("src/main/resources/" + path)));
    }

}