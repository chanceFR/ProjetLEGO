package fr.antoromeochrist.projetlego.utils.bricks;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class Bricks extends Box {

    public static Group groupBricks;
    private Dim dim;
    private int x;
    private int y;
    private int z;

    public Bricks(Dim dim,int x,int y,int z){
        super(dim.getW(),dim.getH(),dim.getD());
        groupBricks.getChildren().add(this);
        this.dim = dim;
        this.x=x;
        this.y=y;
        this.z=z;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
    }

    public Bricks(Dim dim,int x,int y,int z, String hex){
        super(dim.getW(),dim.getH(),dim.getD());
        groupBricks.getChildren().add(this);
        this.dim = dim;
        this.x=x;
        this.y=y;
        this.z=z;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
        this.setMaterial(new PhongMaterial(Color.web(hex)));
    }

    public Bricks(Dim dim,int x,int y,int z,Color c){
        super(dim.getW(),dim.getH(),dim.getD());
        groupBricks.getChildren().add(this);
        this.dim = dim;
        this.x=x;
        this.y=y;
        this.z=z;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.setTranslateZ(z);
        this.setMaterial(new PhongMaterial(c));
    }

    public void setColor(String hex){
        this.setMaterial(new PhongMaterial(Color.web(hex)));
    }

}
