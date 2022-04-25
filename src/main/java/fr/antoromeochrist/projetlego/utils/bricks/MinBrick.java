package fr.antoromeochrist.projetlego.utils.bricks;

import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;

public class MinBrick extends Box {

    private Cylinder cylinder;

    public MinBrick(){
        cylinder = new Cylinder(0.30,0.30);
        cyl();
    }

    public void cyl(){
        cylinder.setTranslateX(this.getTranslateX());
        cylinder.setTranslateY(this.getTranslateY()-0.5f);
        cylinder.setTranslateZ(this.getTranslateZ());
        cylinder.setMaterial(this.getMaterial());
        cylinder.setOpacity(this.getOpacity());
    }
    public Cylinder getCylinder() {
        return cylinder;
    }
}
