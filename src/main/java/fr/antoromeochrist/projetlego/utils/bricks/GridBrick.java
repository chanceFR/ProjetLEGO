package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.utils.P3D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;

public class GridBrick extends ArrayList<Lego> {
    private Volume volume;

    public Volume getVolume() {
        return volume;
    }

    private void createBoxLines(double contW, double contH, double contD, double x, double y, double z) {
        //You call this method to create a box with a size and location you put in
        //This method calls the createLine method for all the sides of your rectangle
        Point3D p1 = new Point3D(x, y, z);
        Point3D p2 = new Point3D(contW + x, y, z);
        Point3D p3 = new Point3D(x, contH + y, z);
        Point3D p4 = new Point3D(contW + x, contH + y, z);
        createLine(p1, p2);
        createLine(p1, p3);
        createLine(p3, p4);
        createLine(p2, p4);

        Point3D p5 = new Point3D(x, y, contD + z);
        Point3D p6 = new Point3D(contW + x, y, contD + z);
        Point3D p7 = new Point3D(x, contH + y, contD + z);
        Point3D p8 = new Point3D(contW + x, contH + y, contD + z);
        createLine(p5, p6);
        createLine(p5, p7);
        createLine(p7, p8);
        createLine(p6, p8);

        createLine(p1, p5);
        createLine(p2, p6);
        createLine(p3, p7);
        createLine(p4, p8);
    }

    double strokewidth = 1;
    private void createLine(Point3D origin, Point3D target) {
        //creates a line from one Point3D to another
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff =  target.subtract(origin);
        double height = diff.magnitude();
        Point3D mid = (Point3D) target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());
        Point3D axisOfRotation = (Point3D) diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);
        Cylinder line = new Cylinder(strokewidth, height);
        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
        Brick.group.getChildren().add(line);
    }
    
    

    public GridBrick(){
        Volume volume = Volume.createAllVolume(new P3D(0,0,0),new Dim(10,10));
        for (int i = 0; i < volume.size(); i++) {
            Lego lego = new Lego();
            lego.setMaterial(new PhongMaterial(Color.RED));
            lego.setWidth(1);
            lego.setHeight(1);
            lego.setDepth(1);
            lego.setTranslateX(volume.get(i).getX());
            lego.setTranslateY(volume.get(i).getY());
            lego.setTranslateZ(volume.get(i).getZ());
            lego.cyl();
            lego.getCylinder().setHeight(lego.getCylinder().getHeight()/2);
            lego.getCylinder().setTranslateY(lego.getCylinder().getTranslateY()+0.5);
            this.add(lego);
        }
        for(Lego lego: this){
            Brick.group.getChildren().add(lego);
            Brick.group.getChildren().add(lego.getCylinder());
        }
        createBoxLines(0,0,0,0,0,0);

    }



}
