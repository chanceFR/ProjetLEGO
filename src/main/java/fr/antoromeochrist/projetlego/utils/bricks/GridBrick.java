package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.utils.P3D;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import java.util.ArrayList;

public class GridBrick extends ArrayList<Lego> {

    private double[] coors;

    public GridBrick(int width,int depth){
        coors=new double[2];
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
        for(int x = -width; x<width;x++){
            for(int z = -depth;z<depth;z++){
                P3D start = new P3D(x,0,z);
                Cylinder c1 =null;
                Cylinder c2 = null;
                if(z%2 == 0) {
                    c1= createCyl(start.add(0, 0.5, 0.5), 1, 0.01);
                    c1.getTransforms().add(rotateZ);
                    c1.setMaterial(new PhongMaterial(Color.web("#808080")));
                }
                if(x %2 == 0) {
                    c2 = createCyl(start.add(-0.5, 0.5, 0), 1, 0.01);
                    c2.getTransforms().add(rotateX);
                    c2.setMaterial(new PhongMaterial(Color.web("#808080")));
                }
                if(c1 != null) Brick.group.getChildren().add(c1);
                if(c2 !=null)Brick.group.getChildren().add(c2);
                Brick.group.getChildren().add(createCyl(start.add(0,0.5,0),0.01,0.30));
                Box b= new Box();
                //b.setHeight(0.01);
                b.setHeight(1);
                b.setWidth(1);
                b.setDepth(1);
                b.setTranslateX(x);
                b.setTranslateY(0.5);
                b.setTranslateZ(z);
                b.setOpacity(0);
                b.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        coors[0]=b.getTranslateX();
                        coors[1]=b.getTranslateZ();
                    }
                });
                Brick.group.getChildren().add(b);
            }
        }
    }

    public double[] getMouseCoors(){
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
