package fr.antoromeochrist.projetlego.utils;

import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Cette classe c'est comme l'objet ColorPicker.
 * Elle sert à stocker l'ancienne couleur !
 */
public class ColorPick extends ColorPicker {

    /**
     * L'ancienne couleur
     */
    private Color oldColor;

    /**
     * Constructeur
     */
    public ColorPick() {
        ColorPicker c = this;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                oldColor = c.getValue();
            }
        });
    }

    /**
     * Avoir l'ancienne couleur
     *
     * @return couleur
     */
    public Color getOldValue() {
        return oldColor;
    }
}
