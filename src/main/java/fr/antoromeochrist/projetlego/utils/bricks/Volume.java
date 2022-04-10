package fr.antoromeochrist.projetlego.utils.bricks;

import eu.hansolo.tilesfx.tools.Point;

import java.util.ArrayList;
import java.util.Arrays;

public class Volume extends ArrayList<PointInt> {

    public void addPoint(int x,int y,int z){
        this.add(new PointInt(x,y,z));
    }
    public void addPoint(PointInt... points){
        System.out.println(this);
        for(PointInt point: Arrays.asList(points)){
            this.add(point);
        }
    }

    public void removePoint(int x,int y,int z){
        this.remove(new PointInt(x,y,z));
    }
    public void removePoint(PointInt... points){
        for(PointInt point: Arrays.asList(points)){
            this.remove(point);
        }
    }

    public String toString(){
        String s = "<";
        for(PointInt p: this){
            s+=p+" ";
        }
        s+=">";
        return s;
    }

}
