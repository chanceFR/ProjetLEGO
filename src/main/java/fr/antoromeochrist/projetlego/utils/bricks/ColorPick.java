package fr.antoromeochrist.projetlego.utils.bricks;

import javafx.event.EventHandler;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class ColorPick extends ColorPicker {

    private Color oldColor;

    public Color getOldValue(){
        return oldColor;
    }

    public ColorPick(){
        ColorPicker c = this;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                oldColor=c.getValue();
            }
        });
    }


}
