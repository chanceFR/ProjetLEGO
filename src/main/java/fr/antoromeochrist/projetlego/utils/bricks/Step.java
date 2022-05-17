package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.utils.print.Fast;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * Cette classe permet de stoker 2 variables.
 * Elle simplifie la gestion des mise à jour des étapes dans "steps".
 * <p>
 * Un booleén pour savoir si l'étape est caché
 * <p>
 * Le texte qui contient le nom de l'étape
 *
 * @see fr.antoromeochrist.projetlego.Controller
 * @see fr.antoromeochrist.projetlego.Model
 */
public class Step {
    /**
     * attributs
     */
    private boolean isHide;
    private String text;
    private ListView<Brick> bricks;
    private TextField tf;

    /**
     * Constructeur avec une String
     */
    public Step(String text, ListView<Brick> bricks,TextField tf) {
        this.isHide = false;
        this.text = text;
        this.bricks = bricks;
        this.tf = tf;
    }
    public Step(String text, ListView<Brick> bricks) {
        this.isHide = false;
        this.text = text;
        this.bricks = bricks;
    }

    /**
     * Mettre à jour le texte
     */
    public void setName(String text,int debug) {
        //Fast.log("-->"+text+" #"+debug);
        this.text = text;
        this.tf.setText(this.text);
    }

    /**
     * Obtenir le nom de l'étape
     *
     * @return string
     */
    public String getName() {
        return this.text;
    }

    /**
     * savoir si l'étape est caché
     *
     * @return booléen
     */
    public boolean isHide() {
        return isHide;
    }

    /**
     * Permet de caché ou non l'étape
     */
    public void hide(boolean hide) {
        isHide = hide;
    }

    /**
     * Permet de mettre à jour le stockage des briques
     *
     * @param bricks
     */
    public void setBricks(ListView<Brick> bricks) {
        this.bricks = bricks;
    }

    public ListView<Brick> getBricks() {
        return bricks;
    }

    public TextField getTf() {
        return tf;
    }

    public void setTf(TextField tf) {
        this.tf = tf;
    }
}