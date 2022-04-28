package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.pieces.Brick;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

/**
 * Une {@link MinBrick} est une box avec un cylindre qui,
 * <p>
 * bouge en même temps(grâce à la méthode cyl()).
 * <p>
 * NB : Une liste de MinBricks forme une {@link Brick}.
 */
public class MinBrick extends Box {

    /**
     * attributs
     */
    private final Cylinder cylinder;

    /**
     * Constructeur
     */
    public MinBrick() {
        cylinder = new Cylinder(0.30, 0.30);
        cyl();
    }

    /**
     * Faire apparaître le cylindre sur la box.
     */
    public void cyl() {
        cylinder.setTranslateX(this.getTranslateX());
        cylinder.setTranslateY(this.getTranslateY() - 0.5f);
        cylinder.setTranslateZ(this.getTranslateZ());
        cylinder.setMaterial(this.getMaterial());
        cylinder.setOpacity(this.getOpacity());
    }

    /**
     * Avoir accès à la liste des cylindres
     */
    public Cylinder getCylinder() {
        return cylinder;
    }
}