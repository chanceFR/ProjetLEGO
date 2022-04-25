package fr.antoromeochrist.projetlego.utils.bricks;

import javafx.scene.control.ListView;

public class Step {
    private boolean isHide;
    private String text;

    public Step(){
        isHide=false;
    }

    public Step(String text){
        this.isHide=false;
        this.text=text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public boolean isHide() {
        return isHide;
    }

    public void hide(boolean hide) {
        isHide = hide;
    }
}
