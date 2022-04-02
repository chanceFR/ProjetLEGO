package fr.antoromeochrist.projetlego.utils.bricks;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

import java.util.ArrayList;

public class Brick extends Box {

    //Permet de relier les briques au controller
    public static Group groupBricks;

    public static ArrayList<Brick> bricks = new ArrayList<>();
    private Dim dim;
    private int x;
    private int y;
    private int z;

    public Brick(Dim dim, int x, int z, int y){
        super(dim.getW(),dim.getH(),dim.getD());
        groupBricks.getChildren().add(this);
        bricks.add(this);
        this.dim = dim;
        //Les coordonnées pour placer le cube en fonction de la place qu'il va prendre
        this.x=x;
        this.y=y;
        this.z=z;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
    }

    public Brick(Dim dim, int x, int z, int y, String hex){
        super(dim.getW(),dim.getH(),dim.getD());
        groupBricks.getChildren().add(this);
        bricks.add(this);
        this.dim = dim;
        this.x=x;
        this.y=y;
        this.z=z;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
        this.setMaterial(new PhongMaterial(Color.web(hex)));
    }

    public Brick(Dim dim, int x, int z,int y, Color c){
        super(dim.getW(),dim.getH(),dim.getD());
        groupBricks.getChildren().add(this);
        bricks.add(this);
        this.dim = dim;
        this.x=x;
        this.y=y;
        this.z=z;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
        this.setMaterial(new PhongMaterial(c));
    }

    public Brick(Dim dim, int x, int z){
        super(dim.getW(),dim.getH(),dim.getD());
        groupBricks.getChildren().add(this);
        bricks.add(this);
        this.dim = dim;
        //Les coordonnées pour placer le cube en fonction de la place qu'il va prendre
        this.x=x;
        this.y=1;
        this.z=z;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
    }

    public Brick(Dim dim, int x, int z, String hex){
        super(dim.getW(),dim.getH(),dim.getD());
        groupBricks.getChildren().add(this);
        bricks.add(this);
        this.dim = dim;
        this.x=x;
        this.y=1;
        this.z=z;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
        this.setMaterial(new PhongMaterial(Color.web(hex)));
    }

    public Brick(Dim dim, int x, int z, Color c){
        super(dim.getW(),dim.getH(),dim.getD());
        groupBricks.getChildren().add(this);
        bricks.add(this);
        this.dim = dim;
        this.x=x;
        this.y=1;
        this.z=z;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
        this.setMaterial(new PhongMaterial(c));
    }
    public void print(){
        System.out.println("("+x+";"+y+";"+z+")");
    }

    public void setColor(String hex){
        this.setMaterial(new PhongMaterial(Color.web(hex)));
    }

}
