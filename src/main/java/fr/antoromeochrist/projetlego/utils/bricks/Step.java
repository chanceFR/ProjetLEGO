package fr.antoromeochrist.projetlego.utils.bricks;

import javafx.scene.control.ListView;

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

    /**
     * Constructeur avec une String
     */
    public Step(String text, ListView<Brick> bricks) {
        this.isHide = false;
        this.text = text;
        this.bricks = bricks;
    }

    /**
     * Mettre à jour le texte
     */
    public void setText(String text) {
        this.text = text;
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
}