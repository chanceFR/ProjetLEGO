package fr.antoromeochrist.projetlego.utils.bricks;

import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

/**
 * Une Minbrick est une box avec un cylindre qui
 * <p>
 * bouge en même temps(grâce à la métholde cyl() ).
 * <p>
 * NB: Une liste de MinBricks forme une Brick.
 */
public class MinBrick extends Box {

    /**
     * attributs
     */
    private Cylinder cylinder;

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
     *
     * @return la liste
     */
    public Cylinder getCylinder() {
        return cylinder;
    }
}