package fr.antoromeochrist.projetlego.utils;

import fr.antoromeochrist.projetlego.utils.bricks.Brick;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point3D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.Arrays;

public class CameraUtils extends PerspectiveCamera {


    public static Rotate x_axis = new Rotate(0,Rotate.X_AXIS);
    public static Rotate y_axis = new Rotate(0,Rotate.Y_AXIS);
    public static Rotate z_axis = new Rotate(0,Rotate.Z_AXIS);

    private Timeline timeline;
    private Translate pivot;
    private int valZoom;

    public CameraUtils (boolean b){
        super (b);
        pivot = new Translate();
        timeline = new Timeline();
        Brick.groupBricks.getChildren().add(this);
        getTransforms().addAll(pivot,x_axis,y_axis,z_axis);
        dezoom(20);
        addRotationsX(new DurationAngle(-10,1));
        addRotationsY(new DurationAngle(-10,1));
    }

    public void addRotationsX( DurationAngle... durationAngles){
       for (DurationAngle d: Arrays.asList(durationAngles)){
           timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(d.getDuration()),new KeyValue(x_axis.angleProperty(), d.getAngle())));
       }
    }
    public void addRotationsY( DurationAngle... durationAngles){
        for (DurationAngle d: Arrays.asList(durationAngles)){
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(d.getDuration()),new KeyValue(y_axis.angleProperty(), d.getAngle())));
        }
    }
    public void addRotationsZ( DurationAngle... durationAngles){
        for (DurationAngle d: Arrays.asList(durationAngles)){
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(d.getDuration()),new KeyValue(z_axis.angleProperty(), d.getAngle())));
        }
    }


    public void stop (){
        timeline.stop();
    }
    public void play (){
        timeline.setCycleCount(1);
        timeline.play();
    }
    public void play (int cycles){
        timeline.setCycleCount(cycles);
        timeline.play();
    }

    public void zoom (double z){
        this.getTransforms().addAll (new Translate(0, 0, z));
    }
    public void dezoom (double z){;
        zoom(-z);
    }



}

