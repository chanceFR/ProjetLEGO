package fr.antoromeochrist.projetlego.utils.bricks;

import javafx.scene.input.MouseButton;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

import static fr.antoromeochrist.projetlego.Controller.grid;
import static fr.antoromeochrist.projetlego.Controller.model;

/**
 * Une {@link MinBrick} est une box avec un cylindre qui,
 * <p>
 * bouge en même temps(grâce à la méthode cyl()).
 * <p>
 * NB : Une liste de thiss forme une {@link Brick}.
 */
public class MinBrick extends Box {
    /**
     * attributs
     */
    private final Cylinder cylinder;
    private final Brick brick;


    /**
     * Constructeur
     */
    public MinBrick(Brick brick) {
        this.brick = brick;
        this.setWidth(1);
        this.setDepth(1);
        this.setHeight(0.5);
        cylinder = new Cylinder(0.30, 0.30);
        cyl();

        this.setOnMouseEntered(mouseEvent -> {
            if (model.brickClicked != null && !model.brickClicked.equals(brick)) {
                grid.setCoors(this.getTranslateX(), this.getTranslateY(), this.getTranslateZ()); //les 2 sont pas plates
            }
        });
        this.getCylinder().setOnMouseEntered(mouseEvent -> {
            if (model.brickClicked != null && !model.brickClicked.equals(brick)) {
                grid.setCoors(this.getTranslateX(), this.getTranslateY(), this.getTranslateZ()); //les 2 sont pas plates
            }
        });
        /*
         * On vient de cliquer sur la brique
         * Permet de d'afficher les bordures
         *
         **/
        this.setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) { //click gauche
                this.brick.manageState(e.getClickCount());
            } else if (e.getButton().equals(MouseButton.SECONDARY))
                if (!(this.brick.isPiece())) this.brick.rotate();
        });
        this.getCylinder().setOnMouseClicked(e -> {
            if (e.getButton().equals(MouseButton.PRIMARY)) { //click gauche
                this.brick.manageState(e.getClickCount());
            } else if (e.getButton().equals(MouseButton.SECONDARY))
                if (!(this.brick.isPiece())) this.brick.rotate();
        });

        this.cyl();
    }

    /**
     * Faire apparaître le cylindre sur la box.
     */
    public void cyl() {
        cylinder.setTranslateX(this.getTranslateX());
        cylinder.setTranslateY(this.getTranslateY() - 0.25f);
        cylinder.setTranslateZ(this.getTranslateZ());
        cylinder.setMaterial(this.getMaterial());
    }

    /**
     * Avoir accès à la liste des cylindres
     */
    public Cylinder getCylinder() {
        return cylinder;
    }
}