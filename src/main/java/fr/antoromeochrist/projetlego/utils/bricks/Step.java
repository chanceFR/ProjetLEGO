package fr.antoromeochrist.projetlego.utils.bricks;

/**
 * Cette classe permet de stoquer 2 variables.
 * Elle simplifit la gestion des mise à jours des étapes dans "steps".
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
     * Constructeur
     */
    public Step() {
        isHide = false;
    }

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
     * @param text
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
     * @param hide
     */
    public void hide(boolean hide) {
        isHide = hide;
    }
}