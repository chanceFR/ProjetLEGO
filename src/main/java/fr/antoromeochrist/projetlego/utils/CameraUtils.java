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
    private Rotate rotate;
    private Timeline timeline;
    private int valZoom;
    public CameraUtils (boolean b){
        super (b);
        rotate = new Rotate();
        timeline = new Timeline();
        Brick.groupBricks.getChildren().add(this);
    }

    public void rotateFor (Point3D axe,DurationAngle... durationAngles){
        rotate.setAxis(axe);
       for (DurationAngle d: Arrays.asList(durationAngles)){
           timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(d.getDuration()),new KeyValue(rotate.angleProperty(), d.getAngle())));
       }


    }
    public void stop (){
        timeline.stop();
    }
    public void play (){
        timeline.play();
    }
    public void zoom (){
        if (valZoom>=(-20)) {
            valZoom+=10;
        }
        this.getTransforms().addAll (
                new Translate(0, 0, valZoom));
    }
    public void dezoom (){
        valZoom-=10;
        this.getTransforms().addAll (
                new Translate(0, 0, valZoom));
    }
    public void delay(float duration){
        rotateFor(Rotate.X_AXIS,new DurationAngle(0,duration));
    }


}

