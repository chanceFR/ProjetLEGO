package fr.antoromeochrist.projetlego.utils;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.utils.print.Fast;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

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
     * Permet d'appliquer des rotations sur la cam??ra
     */
    public final Timeline timeline;

    /**
     * Les angles de la cam??ra
     */
    private float angleX, angleY = 0;

    /**
     * Constructeur
     *
     */
    public CameraUtils(boolean b) {
        super(b);
        timeline = new Timeline();
        Controller.controller.group.getChildren().add(this);
        getTransforms().addAll(x_axis, y_axis, new Rotate(0, Rotate.Z_AXIS));
        dezoom(20);
        timeline.setCycleCount(1);
        timeline.setOnFinished(actionEvent -> {KeyFrame k = timeline.getKeyFrames().get(timeline.getKeyFrames().size()-1);
            KeyFrame k2 = timeline.getKeyFrames().get(timeline.getKeyFrames().size()-2);
            timeline.getKeyFrames().clear();
            timeline.getKeyFrames().add(k2);
            timeline.getKeyFrames().add(k);});
        
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
        for (DurationAngle d : durationAngles) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(d.getDuration()), new KeyValue(x_axis.angleProperty(), d.getAngle())));
            Controller.model.oldAngleX = d.getAngle();
        }

    }

    public void addRotationsY(DurationAngle... durationAngles) {
        for (DurationAngle d : durationAngles) {
            new KeyFrame(Duration.seconds(d.getDuration()), new KeyValue(y_axis.angleProperty(), d.getAngle()));
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(d.getDuration()), new KeyValue(y_axis.angleProperty(), d.getAngle())));
            Controller.model.oldAngleY = d.getAngle();
        }
    }

    /**
     * Translater la cam??ra
     */
    public void translate(double x, double y, double z) {
        this.getTransforms().add(new Translate(x, y, z));
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
        zoom(-a);
    }
}

