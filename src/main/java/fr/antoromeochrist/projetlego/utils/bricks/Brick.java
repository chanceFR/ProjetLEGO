package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.utils.P3D;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Brick extends ArrayList<Box> {

    //Permet de recuperer l'objet group du controller et donc de relier les briques au group du controlleur.
    public static Group group;
    public static HashMap<Brick, String> bricksSortByColors = new HashMap<>();
    public static ArrayList<Brick> environnement = new ArrayList<>();
    private Dim dim;
    private double x;
    private double y;
    private double z;
    private String hex;
    private Volume volume;
    private Color color;
    private String id;

    public Brick(Dim dim, double x, double z) {
        this(dim, x, z, 0, "#808080");
    }

    public Brick(Dim dim, double x, double z, String hex) {
        this(dim, x, z, 0, hex);
    }

    public Brick(Dim dim, double x, double z, double y, String hex) {
        this.id = UUID.randomUUID().toString();
        this.dim = dim;
        this.x = x;
        this.y = y;
        this.z = z;
        createBrick();
        this.setColor(hex);
        Brick b = this;
        for (Box box : this) {
            box.setOnMousePressed(mouseEvent -> {
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

            box.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (this.equals(Controller.brickClicked)) {
                        b.setSelectMode(false);
                    }
                }
            });
        }
    }

    public void createBrick() {
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
            Box box = new Box();
            box.setWidth(1);
            box.setHeight(1);
            box.setDepth(1);
            box.setTranslateX(volume.get(i).getX());
            box.setTranslateY(volume.get(i).getY());
            box.setTranslateZ(volume.get(i).getZ());
            this.add(box);
        }
        group.getChildren().addAll(this);
    }

    public static ArrayList<Brick> getBrickWithColor(String hex) {
        ArrayList<Brick> bricks = new ArrayList<>();
        for (Map.Entry<Brick, String> entry : bricksSortByColors.entrySet()) {
            if (entry.getValue().equals(hex)) {
                bricks.add(entry.getKey());
            }
        }
        return bricks;
    }

    public void setColor(Color color) {
        this.hex = String.format("#%02x%02x%02x", (int) color.getRed(), (int) color.getGreen(), (int) color.getBlue());
        for (Box b : this) b.setMaterial(new PhongMaterial(color));
        this.color = color;
        if (!bricksSortByColors.containsKey(this)) {
            bricksSortByColors.put(this, this.hex);
        }
        if (!bricksSortByColors.get(this).equals(this.hex)) {
            bricksSortByColors.replace(this, this.hex);
        }
    }

    public void setColor(String hex) {
        this.hex = hex;
        if (hex == "") this.hex = "#808080";
        this.color = Color.web(this.hex);
        for (Box box : this) box.setMaterial(new PhongMaterial(color));
        if (!bricksSortByColors.containsKey(this)) {
            bricksSortByColors.put(this, this.hex);
        }
        if (!bricksSortByColors.get(this).equals(this.hex)) {
            bricksSortByColors.replace(this, this.hex);
        }
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
            createBrick();
        }
        for (Box box : this) {
            box.getTransforms().clear();
            box.getTransforms().add(new Translate(this.x, this.y, this.z));
        }
    }

    public String getColor() {
        return hex;
    }

    public Volume getVolume() {
        return volume;
    }

    public String toString() {
        return "[" + hex + "|" + dim + "|" + volume + "]";
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
            for (Box box : this) box.setMaterial(new PhongMaterial(c));
        } else {
            this.setColor(color);
        }
        return this;
    }
}
