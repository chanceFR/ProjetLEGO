package fr.antoromeochrist.projetlego.utils.images;

import javafx.scene.image.Image;
import java.util.ArrayList;

/**
 * Cette classe permet de stocker avec 2 variables image-texte
 */
public class ImageStorage {

    /**
     *  attributs
     */
    private final String text;
    private final ImagePath image;

    /**
     * Constructeur avec les deux attributs
     */

    public ImageStorage(String text, ImagePath image){
        this.text = text;
        this.image = image;
    }

    /**
     * Obtenir le texte
     * @return texte
     */
    public String getText() {
        return text;
    }

    /**
     * Obtenir l'image
     * @return image
     */
    public ImagePath getImage() {
        return image;
    }

    /**
     * Permet de récupérer l'image en fonction du texte
     *
     * @return image
     */
    public static Image getImage(ArrayList<ImageStorage> list, String text){
        for(ImageStorage il : list)
            if(il.getText().replace(" ","").split("\\|")[1].equals(text))
                return il.getImage();
        return null;
    }
}