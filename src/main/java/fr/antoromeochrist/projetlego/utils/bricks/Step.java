package fr.antoromeochrist.projetlego.utils.bricks;

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

    /**
     * Constructeur avec une String
     */
    public Step(String text) {
        this.isHide = false;
        this.text = text;
    }

    /**
     * Mettre à jour le texte
     *
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Obtenir le nom de l'étape
     *
     * @return string
     */
    public String getText() {
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
     *
     */
    public void hide(boolean hide) {
        isHide = hide;
    }
}