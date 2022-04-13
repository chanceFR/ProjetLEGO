package fr.antoromeochrist.projetlego.utils;

import fr.antoromeochrist.projetlego.utils.bricks.Brick;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import java.util.Arrays;

public class CameraUtils extends PerspectiveCamera {


    public static Rotate x_axis = new Rotate(0,Rotate.X_AXIS);
    public static Rotate y_axis = new Rotate(0,Rotate.Y_AXIS);
    public static Rotate z_axis = new Rotate(0,Rotate.Z_AXIS);
    public static KeyFrame lastKF;
    private Timeline timeline;
    private int valZoom;
    private Transform t;

    public CameraUtils (boolean b){
        super (b);
        timeline = new Timeline();
        Brick.group.getChildren().add(this);
        getTransforms().addAll(x_axis,y_axis,z_axis);
        dezoom(20);
        addRotationsX(new DurationAngle(-10,0.001f));
        addRotationsY(new DurationAngle(-10,0.001f));
        timeline.setCycleCount(1);
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                timeline.getKeyFrames().clear();
            }
        });
    }


    public void addRotationsX( DurationAngle... durationAngles){
       for (DurationAngle d: Arrays.asList(durationAngles)){
           lastKF=new KeyFrame(Duration.seconds(d.getDuration()),new KeyValue(x_axis.angleProperty(), d.getAngle()));
           timeline.getKeyFrames().add(lastKF);
       }
       timeline.play();
    }
    public void addRotationsY( DurationAngle... durationAngles){
        for (DurationAngle d: Arrays.asList(durationAngles)){
            lastKF=new KeyFrame(Duration.seconds(d.getDuration()),new KeyValue(y_axis.angleProperty(), d.getAngle()));
            timeline.getKeyFrames().add(lastKF);
        }
        timeline.play();
    }

    public void zoom (double z){
        this.getTransforms().addAll (new Translate(0, 0, z));
    }
    public void dezoom (double z){;
        zoom(-z);
    }

}

