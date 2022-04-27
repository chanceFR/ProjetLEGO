package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.utils.P3D;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class Grid extends ArrayList<MinBrick> {

    /**
     * coordonnées dans la grille en fonction de là ou est la souris sont stocké dedans
     */
    private double[] coors;

    /**
     * Générer une grille
     *
     * @param width largeur
     * @param depth profondeur
     * @param c     couleur
     */
    public Grid(int width, int depth, Color c) {
        coors = new double[3];
        Rotate rotateX = new Rotate();
        rotateX.setAngle(90);
        rotateX.setPivotX(0);
        rotateX.setPivotY(0);
        rotateX.setAxis(Rotate.X_AXIS);
        Rotate rotateZ = new Rotate();
        rotateZ.setAngle(90);
        rotateZ.setPivotX(0);
        rotateZ.setPivotY(0);
        rotateZ.setAxis(Rotate.Z_AXIS);
        for (int x = -width; x < width; x++) {
            for (int z = -depth; z < depth; z++) {
                P3D start = new P3D(x, 0, z);
                if (z % 2 == 0) {
                    Cylinder c1 = createCyl(start.add(0, 0.5, 0.5), 1, 0.01);
                    c1.getTransforms().add(rotateZ);
                    c1.setMaterial(new PhongMaterial(c));
                    Controller.me.group.getChildren().add(c1);
                }
                if (x % 2 == 0) {
                    Cylinder c2 = createCyl(start.add(-0.5, 0.5, 0), 1, 0.01);
                    c2.getTransforms().add(rotateX);
                    c2.setMaterial(new PhongMaterial(c));
                    Controller.me.group.getChildren().add(c2);
                }
                Cylinder c4 = createCyl(start.add(0, 0.5, 0), 0.01, 0.30);
                c4.setMaterial(new PhongMaterial(c));
                Controller.me.group.getChildren().add(c4);
                Box b = new Box();
                b.setHeight(0.01);
                b.setWidth(1);
                b.setDepth(1);
                b.setTranslateX(x);
                b.setTranslateY(0);
                b.setTranslateZ(z);
                b.setOpacity(0);
                b.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        setCoors(b.getTranslateX(), b.getTranslateY(), b.getTranslateZ());
                    }
                });
                b.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(Controller.model.brickClicked != null){
                            if (Controller.model.brickClicked.getState().equals(State.SHOW_IS_SELECT)) {
                                Controller.model.brickClicked.setState(State.FOLLOW_THE_MOUSE);
                            } else {
                                Controller.model.brickClicked.setState(State.SHOW_IS_SELECT);
                            }
                        }
                    }
                });

                Controller.me.group.getChildren().add(b);
            }
        }
    }

    /**
     * Mettre les coordonnes de la box dans coors
     *
     * @param b la box
     */
    public void setCoors(Box b) {
        setCoors(b.getTranslateX(), b.getTranslateY(), b.getTranslateZ());
    }

    /**
     * Mettre les coordonnées dans coors
     *
     * @param x coordonné
     * @param y coordonné
     * @param z coordonné
     */
    public void setCoors(double x, double y, double z) {
        coors[0] = x;
        coors[1] = y;
        coors[2] = z;
    }

    /**
     * Obtenir les coordonnées de là ou se situe la souris dans la grille
     */
    public double[] getMouseCoors() {
        return coors;
    }

    private Cylinder createCyl(P3D p, double height, double radius) {
        Cylinder c = new Cylinder();
        c.setTranslateX(p.getX());
        c.setTranslateY(p.getY());
        c.setTranslateZ(p.getZ());
        c.setHeight(height);
        c.setRadius(radius);
        return c;
    }
}