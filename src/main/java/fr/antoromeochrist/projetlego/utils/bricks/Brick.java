package fr.antoromeochrist.projetlego.utils.bricks;

import fr.antoromeochrist.projetlego.Controller;
import fr.antoromeochrist.projetlego.utils.P3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Brick extends Box {

    //Permet de recuperer l'objet group du controller et donc de relier les briques au group du controlleur.
    public static Group group;
    public static HashMap<Brick,String> bricksSortByColors = new HashMap<>();
    public static ArrayList<Brick> environnement = new ArrayList<>();
    public static Brick lastBrick;
    private static double mouseAnchorX;
    private static double mouseAnchorY;

    private Dim dim;
    private double x;
    private double y;
    private double z;
    private String hex;
    private Volume volume;
    public Brick(){
        this(new Dim(1,1), lastBrick.getX(), lastBrick.getY(), lastBrick.getY(),"#808080");
    }
    public Brick(Dim dim) {
        this(dim, lastBrick.getX(), lastBrick.getY(), lastBrick.getY(),"#808080");
    }
    public Brick(Dim dim, double x, double z) {
        this(dim,x,z,1,"#808080");
    }
    public Brick(Dim dim, double x, double z, String hex) {
        this(dim,x,z,1,hex);
    }
    public Brick(Dim dim, double x, double z, double y, String hex) {
        super(dim.getWeight(), dim.getHeight(), dim.getDepth());
        lastBrick= this;
        group.getChildren().add(this);
        this.dim = dim;
        this.y+=y;
        this.y+=this.y*0.5;
        this.move(getAvailablePlace(this.dim,new P3D(x,y,z)));
        this.setColor(hex);
        this.setOnMousePressed(mouseEvent -> {
            Controller.brickClicked=this;
            System.out.println("--> "+Controller.brickClicked);
        });
    }

    public static ArrayList<Brick> getBrickWithColor(String hex){
        ArrayList<Brick> bricks = new ArrayList<>();
        for(Map.Entry<Brick,String> entry: bricksSortByColors.entrySet()){
            if(entry.getValue().equals(hex)){
                bricks.add(entry.getKey());
            }
        }
        return bricks;
    }
    public static boolean isTaken(P3D point){
        for (Brick b: environnement) { //toutes les briques avec leur volume
            for(P3D pi : b.getVolume()){
                if(point.getX() == pi.getX() && point.getY() == pi.getY() && point.getZ() == pi.getZ() ) {
                    return true;
                }
            }
        }
        return false;
    }

    public static P3D getAvailablePlace(Dim dim, P3D start) {
        double oldSx = start.getX();
        double oldSy = start.getY();
        double oldSz = start.getZ();
        double sx=oldSx;
        double sy=oldSy;
        double sz=oldSz;
        Volume currentV = new Volume();
        boolean volumePasLibre = true;
        while (volumePasLibre) {
            currentV.clear();
            for (int i = 0; i < dim.getWeight(); i++) {
                for (int j = 0; j < dim.getHeight(); j++) {
                    for (int k = 0; k < dim.getDepth(); k++) {
                        P3D np = new P3D(sx + i, sy + j, sz + k);
                        if (!isTaken(np)) {
                            currentV.addPoint(np);
                            if(currentV.size() == dim.getSizeOfPointToNeed()){
                                volumePasLibre=false;
                            }
                        } else {
                            sy+=1;
                        }
                    }
                }
            }
        }
        if(sx != oldSx || sy != oldSy || sz!=oldSz){
            System.out.println("Nouvelle coordonné : "+sx+" "+sy+" "+sz+".");
            return new P3D(sx,sy,sz);
        }
        return start;
    }

    public void setColor(Color c) {
        this.hex = String.format("#%02x%02x%02x", (int)c.getRed(), (int)c.getGreen(), (int)c.getBlue());
        setColor(hex);
    }
    public void setColor(String hex) {
        this.hex = hex;
        this.setMaterial(new PhongMaterial(Color.web(hex)));
        if(!bricksSortByColors.containsKey(this)){
            bricksSortByColors.put(this,this.hex);
        }
        if (!bricksSortByColors.get(this).equals(this.hex)) {
            bricksSortByColors.replace(this, this.hex);
        }
    }

    public void move(P3D point){
        move(point.getX(),point.getY(),point.getZ());
    }


    public void move(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);

        if (this.volume == null) {
            this.volume = new Volume();
        }
        for (double i = 0; i < this.dim.getWeight(); i++) {
            for (double j = 0; j < this.dim.getHeight(); j++) {
                for (double k = 0; k < this.dim.getDepth(); k++) {
                    this.volume.addPoint(this.x + i, this.y + j, this.z + k);
                }
            }
        }
        //sécurité, on sait jamais si on enlève la brique par erreur du dictionnaire
        // à un autre endroit du programme
        if(!environnement.contains(this)){
            environnement.add(this);
        }
    }

    public String getColor() {
        return hex;
    }

    public Volume getVolume() {
        return volume;
    }

    public String toString(){
        return "["+hex+"|"+dim+"|"+volume+"]";
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

    public void up(){
        this.move(this.x,this.y+1,this.z);
    }
    public void down(){
        this.move(this.x,this.y-1,this.z);
    }

    public void left(){
        this.move(this.x+1,this.y,this.z);
    }
    public void right(){
        this.move(this.x-1,this.y,this.z);
    }

}
