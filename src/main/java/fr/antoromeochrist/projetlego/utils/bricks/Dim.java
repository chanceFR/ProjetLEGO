package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.utils.print.Fast;

import java.util.Arrays;

/**
 * L'objet dimension a trois attributs.
 *
 * <p>
 * Ordre des attributs dans le contructeur: largeur x profondeur x hauteur
 * <p>
 * Règle: quand hauteur = 1 (on le note pas)
 * <p>
 * Exemples:
 * <p>
 * - 1X2
 * <p>
 * Exemples 2:
 * <p>
 * - 2x1x2
 * <p>
 * Cette classe permet de générer le volume nécessaire
 * <p><
 * dont aura besoin une brique lors de sa création/déplacement.
 *
 * @see Brick
 */
public class Dim {

    /**
     * attributs
     */
    private int width, depth;
    private double height;

    /**
     * Constructeur sans préciser la hauteur
     * <p>
     * Hauteur à 1 par défaut
     */
    public Dim(int width, int depth) {
        this.width = width;
        this.height = 1;
        this.depth = depth;
    }

    public Dim(Dim dim) {
        this(dim.getWidth(), dim.getDepth(), dim.getHeight());
    }

    /**
     * Constructeur complet pour générer une dimension
     */
    public Dim(int width, int depth, double height) {
        this.width = width;
        this.depth = depth;
        this.height = height;
        this.isReverse = false;
    }

    /**
     * Retourne un objet dimension à partir d'un texte
     */
    public static Dim getDimWithText(String text) {
        String texts[] = text.replace(" ","").split("\\|");
        String[] s = texts[1].split("x");
        if (s.length == 3)
            return new Dim(Integer.parseInt(s[0]), Integer.parseInt(s[1]), Double.parseDouble(s[2]));
        else
            return new Dim(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
    }

    /**
     * Avoir la largeur
     */
    public int getWidth() {
        return width;
    }

    /**
     * Avoir la hauteur
     */
    public double getHeight() {
        return height;
    }

    /**
     * Avoir la profondeur
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Permet de reverse l'affichage de la dimension
     */
    public boolean isReverse;

    public void rotate() {
        int oldDepth = this.depth;
        this.depth = this.width;
        this.width = oldDepth;
        this.isReverse = !this.isReverse;
    }

    /**
     * Affichage de la dimension
     */
    public String toString() {
        String s;
        if (isReverse) {
            s = "" + depth;
            s += "x" + width;
        } else {
            s = "" + width;
            s += "x" + depth;
        }
        if (height != 1) {
            s += "x" + height;
        }
        return s;
    }

    public boolean equals(Dim d) {
        return d.getHeight() == this.height && d.getWidth() == this.width && d.getDepth() == this.depth;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}