package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.utils.P3D;
import javafx.scene.Group;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Brick extends ArrayList<Lego> {

    //Permet de recuperer l'objet group du controller et donc de relier les briques au group du controlleur.
    public static Group group;
    public static HashMap<Brick, Color> bricksSortByColors = new HashMap<>();
    public static ArrayList<Brick> environnement = new ArrayList<>();
    public static ListView<Color> contentColorsStatic;
    public static ListView<ListView<Brick>> stepsStatic;
    public static ListView<Brick> currentStepStatic;

    private Dim dim;
    private double x;
    private double y;
    private double z;
    private Volume volume;
    private Color color;
    private String id;
    private Rectangle rect;
    private boolean isHide;

    public static ListView<Brick> getStepWhereIsBrick(Brick b){
        for(ListView<Brick> lv : stepsStatic.getItems()){
            for(Brick br : lv.getItems()){
                if(br.getID().equals(b.getID())){
                    return lv;
                }
            }
        }
        return null;
    }


    public Brick(Dim dim, double x, double z, double y, Color c) {
        this.isHide=false;
        this.id = UUID.randomUUID().toString();
        this.dim = dim;
        this.x = x;
        this.y = y;
        this.z = z;
        create();
        this.setColor(c);
        Brick b = this;
        for (Lego lego : this) {
            lego.setOnMousePressed(mouseEvent -> {
                if (Controller.brickClicked != null) {
                    if (Controller.brickClicked.equals(this)) {
                        setSelectMode(false);
                        Controller.brickClicked = null;
                    } else {
                        Controller.brickClicked.setSelectMode(false);
                        Controller.brickClicked = this;
                        System.out.println("--> " + Controller.brickClicked);
                        this.setSelectMode(true);
                    }
                } else {
                    Controller.brickClicked = this;
                    System.out.println("--> " + Controller.brickClicked);
                    this.setSelectMode(true);
                }
            });
            lego.cyl();
        }
        currentStepStatic.getItems().add(this);
    }

    private void create() {
        int i1 = 0;
        volume = Volume.createAllVolume(new P3D(this.x, this.y, this.z), this.dim);
        if(!environnement.isEmpty()){
            while (Volume.volumeIntersection(this, environnement)) {
                i1++;
                volume = Volume.createAllVolume(new P3D(this.x, this.y - i1, this.z), this.dim);
            }
        }
        if (!environnement.contains(this)) environnement.add(this);
        for (int i = 0; i < volume.size(); i++) {
            Lego lego = new Lego();
            lego.setWidth(1);
            lego.setHeight(1);
            lego.setDepth(1);
            lego.setTranslateX(volume.get(i).getX());
            lego.setTranslateY(volume.get(i).getY());
            lego.setTranslateZ(volume.get(i).getZ());
            lego.cyl();
            this.add(lego);
        }
        for(Lego lego: this){
            group.getChildren().add(lego);
            group.getChildren().add(lego.getCylinder());
        }
    }

    public void hide(boolean b){
        isHide=b;
        if(b){
            for(Lego lego : this){
                lego.setOpacity(0);
                lego.cyl();
            }
        } else{
            for(Lego lego: this){
                lego.setOpacity(100);
                lego.cyl();
            }
        }
    }

    public void remove(){
        group.getChildren().removeAll(this);
        bricksSortByColors.remove(this);
        environnement.remove(this);
        if(getBrickWithColor(this.color).isEmpty()){
            contentColorsStatic.getItems().remove(this.color);
        }
        getStepWhereIsBrick(this).getItems().remove(this);
        this.remove();
    }

    public static ArrayList<Brick> getBrickWithColor(Color c) {
        ArrayList<Brick> bricks = new ArrayList<>();
        for (Map.Entry<Brick, Color> entry : bricksSortByColors.entrySet()) {
            if (entry.getValue().equals(c)) {
                bricks.add(entry.getKey());
            }
        }
        return bricks;
    }


    public void setColor(Color color) {
        for (Lego b : this) {
            b.setMaterial(new PhongMaterial(color));
            b.cyl();
        }
        this.color = color;
        if (!bricksSortByColors.containsKey(this)) bricksSortByColors.put(this, this.color);
        if (!bricksSortByColors.get(this).equals(this.color)) bricksSortByColors.replace(this, this.color);
        if(!contentColorsStatic.getItems().contains(this.color)) contentColorsStatic.getItems().add(this.color);
        if(!contentColorsStatic.getItems().contains(this.color)) contentColorsStatic.getItems().add(this.color);
        bricksSortByColors.replace(this,this.color);
    }

    public String getID() {
        return this.id;
    }

    public void translate(double x, double y, double z) {
        Volume temp = Volume.createAllVolume(new P3D(this.x+x,this.y+y,this.z+z),this.dim);
        if(!Volume.volumeIntersection(temp,environnement)) {
            this.x += x;
            this.y += y;
            this.z += z;
            volume = temp;
            for (int i = 0; i < volume.size(); i++) {
                get(i).setTranslateX(volume.get(i).getX());
                get(i).setTranslateY(volume.get(i).getY());
                get(i).setTranslateZ(volume.get(i).getZ());
                get(i).cyl();
            }
        }
    }

    public void up() {
        this.translate(0, -1, 0);
    }

    public void down() {
        this.translate(0, 1, 0);
    }

    public void left() {
        this.translate(-1, 0, 0);
    }

    public void right() {
        this.translate(1, 0, 0);
    }

    public void moveForceAt(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        if (isEmpty()) {
            create();
        }
        for (Lego lego : this) {
            lego.getTransforms().clear();
            lego.getTransforms().add(new Translate(this.x, this.y, this.z));
            lego.cyl();
        }
    }

    public Color getColor(){
        return color;
    }

    public Volume getVolume() {
        return volume;
    }

    public String toString() {
        return "[" + color + "|" + dim + "|" + volume + "]";
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

    public Brick setSelectMode(boolean b) {
        if (b) {
            double red  = color.getRed()*255;
            double green = color.getGreen()*255;
            double blue = color.getBlue()*255;
            int i =0;
            int j =0;
            int k=0;
            while(red-i>0 && i < 50){
                i++;
            }
            while(green-j>0 && j < 50){
                j++;
            }
            while(blue-k>0 && k < 50){
                k++;
            }
            red-=i;
            green-=j;
            blue-=k;
            Color c = Color.rgb((int)red,(int)green,(int)blue);
            for (Lego lego : this){
                lego.setMaterial(new PhongMaterial(c));
                lego.cyl();
            }
        } else {
            this.setColor(color);
        }
        return this;
    }

    public Dim getDim() {
        return dim;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public boolean isHide() {
        return isHide;
    }
}
