package fr.antoromeochrist.projetlego.utils;

import fr.antoromeochrist.projetlego.Controller;
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

/*
                                                                            @@@(
                                                                       @@@/ @@@@@@
                                                                     @. @,     @@@
                                                                     @  # %@(   (
                              @@@@@@.                                @% # @
                           @@          .@@@            &&@@@@@@@@@@  ,@ # @@
                           @ @@     (((@@@@@/                        @@*   @
                            &@,@&      @@ @@      ,      %@@@@@@@       @@@
                              @          @              @@@@@@@@@@     @
                              .@ *,       @@             @@@@@@*       @#
                    @@@@####@% @#   &@     #@ .@                        @
                  @@  @@@@@    @  @   @     @& @@                  @@   @         &@@@@@@@
                  @  @@@@@@@@   (/ @   @     @ ,@@@       %@@@@     %@@,      @@@@@@@@@@,
                  @  @ @@@@@@@   @ @   @     @  @@@@@@@@(     @@@@     .@@( /@@@@@@@(
                  @, @ &@@@@@@   @ @ %@@     @  @@     .@@@,     @  ,@@
                   @@ @@    @@  @@@@@@       @   &@@@&        %@@@@@
                     @@  @@@@@@@      &/@@@@@//  *@               .@
                                                  @@@@@@,,,,,,@@@@@@
                                                 @#  @/  @   @@@   @%
                                                @@  @%   @   @ @@   @
                                               ,@  *@    @   @  @@   @
                                               @   @     @   @   @@  ,@
                                              @,  @      @,  @    @(  @#
                                             @@  @       @,  @     @   @
                                             @  @#       #@  @      @   @
                                            @  .&         @  @       @   @
                                           @(  @          @  @        @  /(
                                          @@  @           @  @         @  @
                                          @  @            @  @          @  @
                                         @  @             @, @           @  @
                                        @  ,%             @, @            @ ((
                                        @  @              %@ @             @ @
                                       @  @                                (@ @
                                       (#                                    #%
*/
public class CameraUtils extends PerspectiveCamera {
    /**
     * axes statiques
     */
    public static Rotate x_axis = new Rotate(0, Rotate.X_AXIS);
    public static Rotate y_axis = new Rotate(0, Rotate.Y_AXIS);

    /**
     * Permet d'appliquer des rotations sur la caméra
     */
    private Timeline timeline;

    /**
     * Permet de zoomer sur la caméra et de la bouger
     */
    private Transform t;
    /**
     * Les coordonnées de la caméra
     */
    private double x, y, z;
    /**
     * Les angles de la caméra
     */
    private float angleX, angleY = 0;

    /**
     * Constructeur
     *
     * @param b
     */
    public CameraUtils(boolean b) {
        super(b);
        timeline = new Timeline();
        Controller.me.group.getChildren().add(this);
        getTransforms().addAll(x_axis, y_axis, new Rotate(0, Rotate.Z_AXIS));
        dezoom(20);
        timeline.setCycleCount(1);
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                timeline.getKeyFrames().clear();
            }
        });
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public void setAngleY(float angleY) {
        this.angleY = angleY;
    }

    public void setAngleX(float angleX) {
        this.angleX = angleX;
    }

    public float getAngleY() {
        return angleY;
    }

    public float getAngleX() {
        return angleX;
    }

    public void addRotationsX(DurationAngle... durationAngles) {
        for (DurationAngle d : Arrays.asList(durationAngles)) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(d.getDuration()), new KeyValue(x_axis.angleProperty(), d.getAngle())));
        }
        timeline.play();
    }

    public void addRotationsY(DurationAngle... durationAngles) {
        for (DurationAngle d : Arrays.asList(durationAngles)) {
            new KeyFrame(Duration.seconds(d.getDuration()), new KeyValue(y_axis.angleProperty(), d.getAngle()));
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(d.getDuration()), new KeyValue(y_axis.angleProperty(), d.getAngle())));
        }
        timeline.play();
    }

    /**
     * Translater la caméra
     */
    public void translate(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        this.getTransforms().add(new Translate(x, y, z));
        System.out.println("Tr: " + this.x + " " + this.y + " " + this.z);
    }

    /**
     * Translation de -1 sur y
     */
    public void up() {
        this.translate(0, -1, 0);
    }

    /**
     * Translation de 1 sur y
     */
    public void down() {
        this.translate(0, 1, 0);
    }

    /**
     * Translation de -1 sur x
     */
    public void left() {
        this.translate(-1, 0, 0);
    }

    /**
     * Translation de 1 sur x
     */
    public void right() {
        this.translate(1, 0, 0);
    }

    /**
     * Translation de a sur z
     */
    public void zoom(double a) {
        translate(0, 0, a);
    }

    /**
     * Translation de -a sur z
     */
    public void dezoom(double a) {
        ;
        zoom(-a);
    }
}

